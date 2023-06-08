package com.tasya.myapplication.faq

data class FAQData(
    val title: String,
    val desc: String,
    var isExpandable: Boolean = false
)