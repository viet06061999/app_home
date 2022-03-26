package com.apion.apionhome.utils
import android.util.Patterns.PHONE
import java.util.regex.Matcher
import java.util.regex.Pattern

const val PASSWORD_MIN_LENGTH = 5
const val REGEX_SPACE = "\\s+"
const val PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b"
fun String.removeSpecific(): String {
    var s = this
    return s.trim()
        .replace(",", "")
        .replace(".", "")
        .replace(";", "")
        .filterNot { it.isWhitespace() }
//    s = s.apply {
//        trim()
//        replace(",", "")
//        replace(".", "")
//        replace(";", "")
//    }.filterNot { it.isWhitespace() }
//    return s
}


val String.isPhoneValid get() = Pattern.compile(PHONE_PATTERN).matcher(this).matches() || isNullOrBlank()
//val String.isPhoneValid = true


val String.isValidPassword get() = !contains(REGEX_SPACE) && length > PASSWORD_MIN_LENGTH || isNullOrBlank()
fun String.smartContains(query: String): Boolean {
    if (query.isEmpty()) return true
    return this.contains(query, true)
}