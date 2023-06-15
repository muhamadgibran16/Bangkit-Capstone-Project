package com.example.donorgo.helper

import java.text.SimpleDateFormat
import java.util.*

object ValidationLastDate {
    fun isMoreThanThreeMonths(lastDonorDate: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val threeMonthsAgo = Calendar.getInstance()
        threeMonthsAgo.add(Calendar.MONTH, -3)
        val lastDonorDateTime = dateFormat.parse(lastDonorDate)

        return lastDonorDateTime.before(threeMonthsAgo.time) || lastDonorDateTime == threeMonthsAgo.time
    }
}