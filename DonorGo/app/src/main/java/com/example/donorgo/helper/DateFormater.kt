package com.example.donorgo.helper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormater {
    fun formatDate(currentDate: String): String? {
        val currentFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val targetFormat = "dd MMM yyyy | HH:mm"
        val timezone = "GMT"
        val currentDf: DateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        currentDf.timeZone = TimeZone.getTimeZone(timezone)
        val targetDf: DateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())
        var targetDate: String? = null
        Log.w("detail", "date18: ${currentDf.parse(currentDate)}")
        try {
            val date = currentDf.parse(currentDate)
            Log.w("detail", "date20: $date")
            if (date != null) {
                targetDate = targetDf.format(date)
            }
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return targetDate
    }

    fun formatDateToISO(currentDate: String): String? {
        val outputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        val currentFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val targetFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())

        val date = currentFormat.parse(currentDate)
        return date?.let { targetFormatter.format(it) }
    }

    fun countingTheNextThreeMonths(currentDate: String): String? {
        val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val initialDate = currentFormat.parse(currentDate)

        val calendar = Calendar.getInstance()
        if (initialDate != null) calendar.time = initialDate
        calendar.add(Calendar.MONTH, 3)

        val futureDate = calendar.time
        val formattedFutureDate = currentFormat.format(futureDate)
        return formatDate(formattedFutureDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatNumberMonthToString(currentDate: String): String? {
        val currentFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = LocalDate.parse(currentDate, currentFormat)
        val targetFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return date.format(targetFormatter)
    }

}