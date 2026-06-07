package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.NumberSystem
import com.faangx.ktp.basics.NumberSystemConverter

@Composable
fun NumberSystemConverterDemo() {
    NumberSystemConverter(::convertNumber)
}

// A function which converts a number in some NumberSystem to another NumberSystem
fun convertNumber(num: String, from: NumberSystem, to: NumberSystem): String {

    return when {
        // If the source is Decimal, directly convert to the destination base
        from == NumberSystem.Decimal -> fromDecimal(num.toInt(), to.base)

        // If the destination is Decimal, directly convert from source to Decimal
        to == NumberSystem.Decimal -> toDecimal(num, from.base).toString()

        // Else, convert via Decimal
        else -> {
            // Convert the input number from source base to decimal
            val decimalNum = toDecimal(num, from.base)
            // Convert from decimal to the destination base and return
            fromDecimal(decimalNum, to.base)
        }
    }
}

// Helper function to convert a number of any base to decimal
private fun toDecimal(num: String, base: Int): Int {
    var decimal = 0
    var power = 1 // Initialize the power of base as 1
    var idx = num.length - 1 // Start from the last digit of the number

    // Iterate from last to first digit
    while (idx >= 0) {
        // Calculate the value of the digit in the number
        val digitValue = if (Character.isDigit(num[idx])) {
            // If digit, subtract ASCII value of '0'
            num[idx] - '0'
        } else {
            // If alphabet, subtract ASCII value of 'A' and add 10
            num[idx].uppercaseChar() - 'A' + 10
        }
        // Add the weighted digit value to the decimal result
        decimal += digitValue * power
        // Update power for the next iteration
        power *= base
        // Move to the next digit
        idx--
    }
    return decimal
}

// Helper function to convert a number from decimal to any other base
private fun fromDecimal(decimal: Int, base: Int): String {
    var out = ""
    var num = decimal // Initialize variable for the iterative conversion

    // Perform division and remainder operations until num is not zero
    while (num > 0) {
        val digitValue = num % base // Get remainder (this will be the next digit value)

        // Convert the digit value to the corresponding symbol and prepend to output string
        out = if (digitValue < 10) {
            digitValue.toString() + out
        } else {
            // If digitValue is more than or equal to 10, derive corresponding alphabet by subtracting 10 from digitValue, adding to 'A' ASCII value and converting to char
            (digitValue - 10 + 'A'.toInt()).toChar().toString() + out
        }

        num /= base // Update num by performing integer division with base
    }
    // If no conversion was performed, return "0", else return the output string
    return out.ifEmpty { "0" }
}
