import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toTypeName
import ksp.KtFmtFormatter
import ksp.MiniApp
import ksp.MiniAppTest
import java.io.OutputStream
import java.util.*

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

        val miniAppTestSymbols = resolver.getSymbolsWithAnnotation(MiniAppTest::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()

        logger.warn("found ${miniAppTestSymbols.toList().size}")

        // Defer MiniAppTest symbols processing until the next round
        deferredSymbols.addAll(miniAppTestSymbols)

        return deferredSymbols
    }

    private val deferredSymbols = mutableListOf<KSClassDeclaration>()
    private val completedSymbols = mutableListOf<KSClassDeclaration>()

    override fun finish() {
        deferredSymbols.forEach { symbol ->
            if (symbol.validate() && !completedSymbols.contains(symbol)) {
                val packageName = symbol.packageName.asString()
                val fileSpecBuilder = FileSpec.builder(packageName, symbol.simpleName.asString())
                generateTestExtensionFunction(symbol, fileSpecBuilder)
                val fileSpec = fileSpecBuilder.build()
                try {
                    val file = codeGenerator.createNewFile(
                        dependencies = Dependencies(false, symbol.containingFile!!),
                        packageName = packageName,
                        fileName = symbol.simpleName.asString() + "Ext"
                    )
                    file.writeAll(fileSpec)
                } catch (e: FileAlreadyExistsException) {

                }

                completedSymbols.add(symbol)
            }
        }
    }

    private fun processFunction(function: KSFunctionDeclaration) {
        val miniAppAnnotation = function.annotations.find { it.shortName.asString() == "MiniApp" }
        val nameArg = miniAppAnnotation?.arguments?.find { it.name?.asString() == "name" }?.value as? String ?: ""
        val paramNamesArg = miniAppAnnotation?.arguments?.find { it.name?.asString() == "paramNames" }?.value as? String ?: ""

        val packageName = function.packageName.asString()
        val functionName = function.simpleName.asString()
        val interfaceName = "${functionName}Functionality"
        val strFunsName = "${functionName.replaceFirstChar { it.lowercaseChar() }}Functionality"
        val implClassName = "${functionName}FunctionalityImpl"

        val fileSpecBuilder = FileSpec.builder(packageName, interfaceName)

        val interfaceCode = generateInterface(function, interfaceName, paramNamesArg, fileSpecBuilder)
        val topLevelFunctionsCode = generateTopLevelFunctions(function, paramNamesArg, fileSpecBuilder)
        val implClassCode = generateImplClass(function, interfaceName, implClassName, paramNamesArg, fileSpecBuilder)
        generateComposableFunction(function, interfaceName, fileSpecBuilder)
        generateMiniAppComposableFunction(function, nameArg, fileSpecBuilder)
        generateMobileMiniAppFunction(function, nameArg, fileSpecBuilder)

        fileSpecBuilder.addFunction(generateStringRepresentation("${strFunsName}_Interface", interfaceCode))
        fileSpecBuilder.addFunction(generateStringRepresentation("${strFunsName}_Impl", implClassCode))
        fileSpecBuilder.addFunction(generateStringRepresentation("${strFunsName}_Funs", topLevelFunctionsCode))

        val fileSpec = fileSpecBuilder.build()
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, function.containingFile!!),
            packageName = packageName,
            fileName = interfaceName
        )
        file.writeAll(fileSpec)
    }

    private fun OutputStream.writeAll(fileSpec: FileSpec) {
        bufferedWriter().use { writer ->
            var content = captureGeneratedCode { stringBuilder ->
                fileSpec.writeTo(stringBuilder)
            }
            content = KtFmtFormatter.formatCode(content)
            writer.write(
                content.replace("public ", "")
                    .replace(": Unit", "")
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

//            fileSpecBuilder.addFunction(funSpec)
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
//        fileSpecBuilder.addType(typeSpec)

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
        val miniAppFunctionName = functionName.replace("App", "") + "MiniApp"

        // Generate parameter list with correct types
        val parameters = function.parameters.map { param ->
            val paramName = param.name?.asString() ?: ""
            val paramType = param.type.resolve()
            val paramTypeName = if (paramType.declaration.qualifiedName?.asString() == "kotlin.Function0") {
                LambdaTypeName.get(
                    returnType = paramType.arguments[0].type?.resolve()?.toTypeName() ?: UNIT
                )
            } else {
                paramType.toTypeName()
            }
            ParameterSpec.builder(paramName, paramTypeName).build()
        }

        // Generate parameter names for lambda invocation
        val lambdaParameters = function.parameters.joinToString(", ") { parameter ->
            "${parameter.name?.asString()}"
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
        val interfaceName = "${functionName}Functionality"
        val strFunsName = "${functionName.replaceFirstChar { it.lowercaseChar() }}Functionality"
        val packageName = function.packageName.asString()

        val funSpec = FunSpec.builder(functionName.replace("App", "") + "_MobileMiniApp")
            .returns(ClassName("com.faangx.ktp", "MobileMiniApp").parameterizedBy(ClassName(packageName, interfaceName)))
            .addStatement(
                """
            return MobileMiniApp(
                label = %S,
                functionalityClass = %L::class.java,
                functionalityInterface = ${strFunsName}_Interface_AsString(),
                functionalityImpl = ${strFunsName}_Impl_AsString(),
                functionalityFuns = ${strFunsName}_Funs_AsString(),
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

    private fun generateTestExtensionFunction(objectDeclaration: KSClassDeclaration, fileSpecBuilder: FileSpec.Builder) {
        val objectName = objectDeclaration.simpleName.asString()
        val testFunction = objectDeclaration.getAllFunctions().find { it.simpleName.asString() == "test" }
        val functionalityParam = testFunction?.parameters?.find { it.type.resolve().declaration is KSClassDeclaration }
        val functionalityInterface = functionalityParam?.type?.resolve()?.declaration as? KSClassDeclaration
        val functionalityName = functionalityInterface?.simpleName?.asString() ?: return

        fileSpecBuilder.addImport("com.faangx.ktp.basics", functionalityName)

        val funBuilder = FunSpec.builder("test")
            .receiver(ClassName("com.faangx.ktp.test", objectName))

        val testcaseParam = testFunction.parameters.find { it.name?.asString() == "testcase" }
        if (testcaseParam != null) {
            funBuilder.addParameter("testcase", ClassName("", testcaseParam.type.toTypeName().toString()))
        }

        val parametersCode = functionalityInterface.getAllFunctions()
            .filter { it.simpleName.asString() !in listOf("equals", "hashCode", "toString") }
            .joinToString("\n") { func ->
            val funcName = func.simpleName.asString()
            val params = func.parameters.joinToString(", ") { param ->
                val paramName = param.name?.asString() ?: ""
                val paramType = param.type.resolve().declaration.simpleName.asString()
                "$paramName: $paramType"
            }
            funBuilder.addParameter(
                funcName.replaceFirstChar { it.lowercase(Locale.getDefault()) }
                    .trimEnd('1'),
                LambdaTypeName.get(
                    returnType = func.returnType!!.resolve().toTypeName(),
                    parameters = func.parameters.map { it.type.resolve().toTypeName() }.toTypedArray()
                )
            )
            """
        override fun $funcName(${params}): ${func.returnType!!.resolve().toTypeName()} {
            return ${funcName.trimEnd('1')}(${func.parameters.joinToString(", ") { it.name!!.asString() }})
        }
        """.trimIndent()
        }

        funBuilder.addStatement(
            """
        test(
            object : $functionalityName {
                $parametersCode
            }
            ${if (testcaseParam != null) ",\ntestcase" else ""}
        )
        """.trimIndent()
        )

        fileSpecBuilder.addFunction(funBuilder.build())
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
