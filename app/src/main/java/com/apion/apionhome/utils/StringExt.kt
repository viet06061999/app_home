package com.apion.apionhome.utils

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

fun String.smartContains(query: String): Boolean {
    println("raw $this query $query")
    if (query.isEmpty()) return true
    return this.contains(query, true)
}