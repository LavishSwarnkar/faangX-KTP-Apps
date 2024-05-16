package ksp

import FunctionalityProcessor
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated

class FunctionalityProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return FunctionalityProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}