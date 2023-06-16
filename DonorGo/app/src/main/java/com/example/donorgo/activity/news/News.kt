package com.example.donorgo.activity.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var url: String = "",
    var title: String = "",
    var date: String = "",
    var photo: Int = 0
) : Parcelable