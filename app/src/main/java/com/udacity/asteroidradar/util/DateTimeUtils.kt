package com.udacity.asteroidradar.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateTimeUtils {
    fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)

    fun getWeekDate(): String {
        val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_YEAR, 7)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.time)
    }
}