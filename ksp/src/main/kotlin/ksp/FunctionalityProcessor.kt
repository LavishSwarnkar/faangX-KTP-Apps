import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import ksp.GenerateFunctionality

class FunctionalityProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(GenerateFunctionality::class.qualifiedName!!)
            .filterIsInstance<KSFunctionDeclaration>()

        symbols.forEach { symbol ->
            if (symbol.validate()) {
                processFunction(symbol)
            }
        }

        return emptyList()
    }

    private fun processFunction(function: KSFunctionDeclaration) {
        val packageName = function.packageName.asString()
        val functionName = function.simpleName.asString()
        val interfaceName = "${functionName}Functionality"
        val implClassName = "${functionName}FunctionalityImpl"

        val fileSpecBuilder = FileSpec.builder(packageName, interfaceName)

        val interfaceCode = generateInterface(function, interfaceName, fileSpecBuilder)
        val topLevelFunctionsCode = generateTopLevelFunctions(function, fileSpecBuilder)
        val implClassCode = generateImplClass(function, packageName, interfaceName, implClassName, fileSpecBuilder)
        generateComposableFunction(function, interfaceName, fileSpecBuilder)

        fileSpecBuilder.addFunction(generateStringRepresentation("${interfaceName}_Interface", interfaceCode))
        fileSpecBuilder.addFunction(generateStringRepresentation("${interfaceName}_Impl", implClassCode))
        fileSpecBuilder.addFunction(generateStringRepresentation("${interfaceName}_Funs", topLevelFunctionsCode))

        val fileSpec = fileSpecBuilder.build()
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, function.containingFile!!),
            packageName = packageName,
            fileName = interfaceName
        )
        file.bufferedWriter().use { writer ->
            fileSpec.writeTo(writer)
        }
    }

    private fun generateInterface(function: KSFunctionDeclaration, interfaceName: String, fileSpecBuilder: FileSpec.Builder): String {
        val interfaceBuilder = TypeSpec.interfaceBuilder(interfaceName)

        function.parameters.forEach { parameter ->
            val paramName = parameter.name?.asString() ?: return@forEach
            val functionSignature = getFunctionSignature(parameter, suffix = "1")
            interfaceBuilder.addFunction(
                FunSpec.builder(paramName + "1")
                    .addModifiers(KModifier.ABSTRACT)
                    .addParameters(functionSignature.first)
                    .returns(functionSignature.second)
                    .build()
            )
        }

        val typeSpec = interfaceBuilder.build()
        fileSpecBuilder.addType(typeSpec)

        return captureGeneratedCode { stringBuilder ->
            FileSpec.builder("", "")
                .addType(typeSpec)
                .build()
                .writeTo(stringBuilder)
        }
    }

    private fun generateTopLevelFunctions(function: KSFunctionDeclaration, fileSpecBuilder: FileSpec.Builder): String {
        val topLevelFunctionsCode = StringBuilder()

        function.parameters.forEach { parameter ->
            val paramName = parameter.name?.asString() ?: return@forEach
            val functionSignature = getFunctionSignature(parameter)
            val funSpec = FunSpec.builder(paramName)
                .addParameters(functionSignature.first)
                .returns(functionSignature.second)
                .addStatement("TODO()")
                .build()

            fileSpecBuilder.addFunction(funSpec)
            topLevelFunctionsCode.append(
                captureGeneratedCode { stringBuilder ->
                    FileSpec.builder("", "")
                        .addFunction(funSpec)
                        .build()
                        .writeTo(stringBuilder)
                }
            )
        }

        return topLevelFunctionsCode.toString()
    }

    private fun generateImplClass(function: KSFunctionDeclaration, packageName: String, interfaceName: String, implClassName: String, fileSpecBuilder: FileSpec.Builder): String {
        val implClassBuilder = TypeSpec.classBuilder(implClassName)
            .addSuperinterface(ClassName(packageName, interfaceName))

        function.parameters.forEach { parameter ->
            val paramName = parameter.name?.asString() ?: return@forEach
            val functionSignature = getFunctionSignature(parameter, suffix = "1")
            implClassBuilder.addFunction(
                FunSpec.builder(paramName + "1")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameters(functionSignature.first)
                    .returns(functionSignature.second)
                    .addStatement("return $paramName(${functionSignature.first.joinToString(", ") { it.name }})")
                    .build()
            )
        }

        val typeSpec = implClassBuilder.build()
        fileSpecBuilder.addType(typeSpec)

        return captureGeneratedCode { stringBuilder ->
            FileSpec.builder("", "")
                .addType(typeSpec)
                .build()
                .writeTo(stringBuilder)
        }
    }

    private fun generateComposableFunction(function: KSFunctionDeclaration, interfaceName: String, fileSpecBuilder: FileSpec.Builder) {
        val functionName = function.simpleName.asString()
        val composableFunctionName = functionName.replaceFirstChar { it.lowercaseChar() }

        val parameters = function.parameters.joinToString(", ") { parameter ->
            val paramName = parameter.name?.asString() ?: return@joinToString ""
            "$paramName = functionality::${paramName}1"
        }

        val funSpec = FunSpec.builder(composableFunctionName)
            .addAnnotation(ClassName("androidx.compose.runtime", "Composable"))
            .addParameter("functionality", ClassName("", interfaceName))
            .addStatement("$functionName($parameters)")
            .build()

        fileSpecBuilder.addFunction(funSpec)
    }

    private fun generateStringRepresentation(name: String, code: String): FunSpec {
        return FunSpec.builder("${name}_AsString")
            .returns(String::class)
            .addStatement("return %P", removeImportStatements(code).trimIndent())
            .build()
    }

    private fun removeImportStatements(code: String): String {
        return code.lines().filterNot { it.trim().startsWith("import") }.joinToString("\n")
    }

    private fun captureGeneratedCode(block: (StringBuilder) -> Unit): String {
        val stringBuilder = StringBuilder()
        block(stringBuilder)
        return stringBuilder.toString()
    }

    private fun getFunctionSignature(parameter: KSValueParameter, suffix: String = ""): Pair<List<ParameterSpec>, TypeName> {
        val functionType = parameter.type.resolve()
        val args = functionType.arguments
        val parameters = args.dropLast(1).mapIndexed { i, arg ->
            val paramType = arg.type?.resolve()?.declaration?.qualifiedName?.asString() ?: "Unknown"
            ParameterSpec.builder("p$i", paramType.toTypeName()).build()
        }
        val returnType = args.last().type?.resolve()?.declaration?.qualifiedName?.asString() ?: "Unknown"
        return parameters to returnType.toTypeName()
    }

    private fun String.toTypeName(): TypeName = when (this) {
        "Int" -> INT
        "Long" -> LONG
        "Short" -> SHORT
        "Byte" -> BYTE
        "Float" -> FLOAT
        "Double" -> DOUBLE
        "Char" -> CHAR
        "Boolean" -> BOOLEAN
        "String" -> STRING
        "Unit" -> UNIT
        else -> ClassName.bestGuess(this)
    }
}