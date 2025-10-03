package com.example.pruebatecnicacajasocial.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getDateFormat(date: String) : Date {
    if (date.isEmpty()) return Date()
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.parse(date) ?: Date()
}