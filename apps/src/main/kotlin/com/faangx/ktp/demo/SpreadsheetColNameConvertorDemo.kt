package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SpreadsheetColNameConvertor
import com.faangx.ktp.basics.SpreadsheetColNameConvertorMiniApp

/**
 * Converts a column number to its corresponding name in Excel format.
 *
 * @param colNo the column number to convert
 * @return the name of the column
 */
fun getSpreadsheetColName(colNo: Int): String {
    // StringBuilder object to build the name of the column.
    val colName = StringBuilder()

    // Assigning the column number to a variable for further manipulation.
    var num = colNo
    // Loop until num is greater than 0
    while(num > 0) {
        // Get the remainder of num when divided by 26
        val rem = num % 26

        // If remainder is 0, append 'Z' to the end and adjust num for the zero remainder
        if(rem == 0) {
            colName.append('Z')
            num = (num / 26) - 1
        } else { // Else, append the corresponding alphabet to the end and divide num by 26
            colName.append((rem - 1 + 'A'.code).toChar())
            num /= 26
        }
    }

    // Reverse the StringBuilder and convert it to String before returning
    return colName.reverse().toString()
}

/**
 * Converts a column name in Excel format to its corresponding number.
 *
 * @param colName The name of the column in Excel format
 * @return The corresponding column number
 */
fun getColNum(colName: String): Int {
    // Variable to store the resultant number
    var num = 0
    // Variable to store the power of 26 (0 to n)
    var power = 0

    // Iterate through each character in the reversed column name
    for (char in colName.reversed()) {
        // Add to num the ASCII value of char minus ASCII value of 'A' plus 1 times 26 to the power of 'power'
        num += Math.pow(26.0, power.toDouble()).toInt() * (char - 'A' + 1)
        // Increment power by 1 after each iteration
        power += 1
    }

    // Return the calculated column number
    return num
}

fun main() {
    SpreadsheetColNameConvertorMiniApp(
        ::getSpreadsheetColName,
        ::getColNum
    )
}

@Composable
fun SpreadsheetColNameConvertorDemo() {
    SpreadsheetColNameConvertor(
        ::getSpreadsheetColName,
        ::getColNum
    )
}