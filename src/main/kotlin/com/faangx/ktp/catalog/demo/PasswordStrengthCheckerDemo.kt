package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.string.PasswordStrengthChecker
import com.faangx.ktp.basics.string.PasswordStrengthCheckResult

fun checkPasswordStrength(password: String): PasswordStrengthCheckResult {
    return buildMap {
        put("1 Uppercase letter", password.any { it in 'A'..'Z' })
        put("1 Lowercase letter", password.any { it in 'a'..'z' })
        put("1 Digit", password.any { it in '0'..'9' })
        put("1 Special character", password.any { !it.isLetterOrDigit() })
        put("Minimum 6 characters long", password.length >= 6)
    }
}

@Composable
fun PasswordStrengthCheckerDemo() {
    PasswordStrengthChecker(
        checkStrength = ::checkPasswordStrength
    )
}