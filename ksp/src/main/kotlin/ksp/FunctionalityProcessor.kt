import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ksp.KtFmtFormatter
import ksp.MiniApp

class FunctionalityProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(MiniApp::class.qualifiedName!!)
            .filterIsInstance<KSFunctionDeclaration>()

        symbols.forEach { symbol ->
            if (symbol.validate()) {
                processFunction(symbol)
            }
        }

        return emptyList()
    }

    private fun processFunction(function: KSFunctionDeclaration) {
        val miniAppAnnotation = function.annotations.find { it.shortName.asString() == "MiniApp" }
        val nameArg = miniAppAnnotation?.arguments?.find { it.name?.asString() == "name" }?.value as? String ?: ""
        val paramNamesArg = miniAppAnnotation?.arguments?.find { it.name?.asString() == "paramNames" }?.value as? String ?: ""

        val packageName = function.packageName.asString()
        val functionName = function.simpleName.asString()
        val interfaceName = "${functionName}Functionality"
        val implClassName = "${functionName}FunctionalityImpl"

        val fileSpecBuilder = FileSpec.builder(packageName, interfaceName)

        val interfaceCode = generateInterface(function, interfaceName, paramNamesArg, fileSpecBuilder)
        val topLevelFunctionsCode = generateTopLevelFunctions(function, paramNamesArg, fileSpecBuilder)
        val implClassCode = generateImplClass(function, interfaceName, implClassName, paramNamesArg, fileSpecBuilder)
        generateComposableFunction(function, interfaceName, fileSpecBuilder)
        generateMiniAppComposableFunction(function, nameArg, fileSpecBuilder)
        generateMobileMiniAppFunction(function, nameArg, fileSpecBuilder)

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
            var content = captureGeneratedCode { stringBuilder ->
                fileSpec.writeTo(stringBuilder)
            }
            content = KtFmtFormatter.formatCode(content)
            writer.write(
                content.replace("public ", "")
            )
        }
    }

    private fun generateInterface(function: KSFunctionDeclaration, interfaceName: String, paramNames: String, fileSpecBuilder: FileSpec.Builder): String {
        val interfaceBuilder = TypeSpec.interfaceBuilder(interfaceName)

        // Split paramNames into individual parameter lists for each function
        val paramNamesList = paramNames.split(";").map { it.trim() }

        function.parameters.forEachIndexed { index, parameter ->
            val paramName = parameter.name?.asString() ?: return@forEachIndexed
            val functionSignature = getFunctionSignature(parameter, suffix = "1")
            val paramInputNames = paramNamesList.getOrNull(index)?.split(",")?.map { it.trim() } ?: listOf("param")

            val parameters = functionSignature.first.mapIndexed { idx, paramSpec ->
                paramSpec.toBuilder(paramInputNames[idx], paramSpec.type).build()
            }

            interfaceBuilder.addFunction(
                FunSpec.builder(paramName + "1")
                    .addModifiers(KModifier.ABSTRACT)
                    .addParameters(parameters)
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

    private fun generateTopLevelFunctions(function: KSFunctionDeclaration, paramNames: String, fileSpecBuilder: FileSpec.Builder): String {
        val topLevelFunctionsCode = StringBuilder()
        val paramNamesList = paramNames.split(";").map { it.trim() }

        function.parameters.forEachIndexed { index, parameter ->
            val paramName = parameter.name?.asString() ?: return@forEachIndexed
            val functionSignature = getFunctionSignature(parameter)
            val paramInputNames = paramNamesList.getOrNull(index)?.split(",")?.map { it.trim() } ?: listOf("param")

            val parameters = functionSignature.first.mapIndexed { idx, paramSpec ->
                paramSpec.toBuilder(paramInputNames[idx], paramSpec.type).build()
            }

            val funSpec = FunSpec.builder(paramName)
                .addParameters(parameters)
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

        return topLevelFunctionsCode.toString().trim()
    }

    private fun generateImplClass(function: KSFunctionDeclaration, interfaceName: String, implClassName: String, paramNames: String, fileSpecBuilder: FileSpec.Builder): String {
        val packageName = function.packageName.asString()
        val implClassBuilder = TypeSpec.classBuilder(implClassName)
            .addSuperinterface(ClassName(packageName, interfaceName))
        val paramNamesList = paramNames.split(";").map { it.trim() }

        function.parameters.forEachIndexed { index, parameter ->
            val paramName = parameter.name?.asString() ?: return@forEachIndexed
            val functionSignature = getFunctionSignature(parameter, suffix = "1")
            val paramInputNames = paramNamesList.getOrNull(index)?.split(",")?.map { it.trim() } ?: listOf("param")

            val parameters = functionSignature.first.mapIndexed { idx, paramSpec ->
                paramSpec.toBuilder(paramInputNames[idx], paramSpec.type).build()
            }

            implClassBuilder.addFunction(
                FunSpec.builder(paramName + "1")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameters(parameters)
                    .returns(functionSignature.second)
                    .addStatement("return $paramName(${paramInputNames.joinToString(", ")})")
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

        val parameters = function.parameters.joinToString(", ") { parameter ->
            val paramName = parameter.name?.asString() ?: return@joinToString ""
            "$paramName = functionality::${paramName}1"
        }

        val funSpec = FunSpec.builder(functionName)
            .addAnnotation(ClassName("androidx.compose.runtime", "Composable"))
            .addParameter("functionality", ClassName("", interfaceName))
            .addStatement("$functionName($parameters)")
            .build()

        fileSpecBuilder.addFunction(funSpec)
    }

    private fun generateMiniAppComposableFunction(function: KSFunctionDeclaration, name: String, fileSpecBuilder: FileSpec.Builder) {
        val functionName = function.simpleName.asString()
        val miniAppFunctionName = functionName + "MiniApp"

        // Generate parameter list with correct types
        val parameters = function.parameters.map { param ->
            val paramName = param.name?.asString() ?: ""
            val paramType = param.type.resolve().arguments[0].type?.resolve()?.declaration?.simpleName?.asString() ?: "Any"
            ParameterSpec.builder(paramName, LambdaTypeName.get(returnType = ClassName("", "Unit"), parameters = *arrayOf(ClassName("", paramType)))).build()
        }

        // Generate parameter names for lambda invocation
        val lambdaParameters = function.parameters.joinToString(", ") { parameter ->
            "::${parameter.name?.asString()}"
        }

        val funSpec = FunSpec.builder(miniAppFunctionName)
            .addParameters(parameters)
            .addStatement(
                """
            MiniApp(
                title = %S,
                composable = {
                    $functionName($lambdaParameters)
                }
            )
            """.trimIndent(), name
            )
            .build()

        fileSpecBuilder.addFunction(funSpec)
        fileSpecBuilder.addImport("com.faangx.ktp", "MiniApp")
    }

    private fun generateMobileMiniAppFunction(function: KSFunctionDeclaration, name: String, fileSpecBuilder: FileSpec.Builder) {
        val functionName = function.simpleName.asString()
        val miniAppFunctionName = functionName.replaceFirstChar { it.lowercaseChar() }
        val interfaceName = "${functionName}Functionality"
        val packageName = function.packageName.asString()

        val funSpec = FunSpec.builder(miniAppFunctionName + "_MobileMiniApp")
            .returns(ClassName("com.faangx.ktp", "MobileMiniApp").parameterizedBy(ClassName(packageName, interfaceName)))
            .addStatement(
                """
            return MobileMiniApp(
                label = %S,
                functionalityClass = %L::class.java,
                functionalityInterface = ${interfaceName}_Interface_AsString(),
                functionalityImpl = ${interfaceName}_Impl_AsString(),
                functionalityFuns = ${interfaceName}_Funs_AsString(),
                functionalityImplClassName = %S,
                packageName = %S,
                composable = { $functionName(it) }
            )
            """.trimIndent(), name, interfaceName, "${interfaceName}Impl", packageName
            )
            .build()

        fileSpecBuilder.addFunction(funSpec)
        fileSpecBuilder.addImport("com.faangx.ktp", "MiniApp")
    }

    private fun generateStringRepresentation(name: String, code: String): FunSpec {
        return FunSpec.builder("${name}_AsString")
            .returns(String::class)
            .addStatement(
                "return %P",
                removeImportStatements(code).trimIndent()
                    .replace(",\n    ", ", ")
                    .replace(",\n  ", ", ")
            )
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