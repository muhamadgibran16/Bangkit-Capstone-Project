package com.tasya.myapplication

data class SyaratDetailData(
    val title: String,
    val logo: Int,
    val desc: String,
    var isExpandable: Boolean = false
)