package com.faangx.ktp.patterns

class Pattern(
    val code: String,
    val type: Type,
    val sample: String,
    val defaultCustomization: String?
) {
    enum class Type(val abbr: String) {
        LinesBased("L"),
        LinesAndCharBased("LC"),
        LinesAnd2CharBased("L2C"),
        WordBased("W");

        fun requiresLinesInput() = this != WordBased
        fun requiresCustomization() = this != LinesBased
        fun customizationLabel(): String = when (this) {
            LinesBased -> error("Customization NA")
            LinesAndCharBased -> "Character"
            LinesAnd2CharBased -> "Characters"
            WordBased -> "Word"
        }

        fun customizationLength() = when (this) {
            LinesBased -> 0
            LinesAndCharBased -> 1
            LinesAnd2CharBased -> 2
            WordBased -> 1
        }
    }

}