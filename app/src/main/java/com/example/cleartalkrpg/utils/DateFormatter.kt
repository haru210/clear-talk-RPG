package com.example.cleartalkrpg.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateToDateString(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatDateToTimeString(date: Date): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return timeFormat.format(date)
}
