package com.apion.apionhome.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

object TimeFormat {
    const val TIME_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val HOUR_FORMAT = "HH:mm"
    const val TIME_FORMAT_APP = "EEE, dd MMM HH:mm"
    const val TIME_FORMAT_APP_EXPAND = "EEE, dd MMM yyyy"
    const val DATE_TIME_FORMAT_APP_FULL = "EEE, dd MMM yyyy HH:mm"
}
