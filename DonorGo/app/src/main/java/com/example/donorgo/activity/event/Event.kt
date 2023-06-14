package com.example.donorgo.activity.event


data class Event (
    var detail : String = "",
    var title : String = "",
    var status : String = "",
    var location : String = "",
    var date: String = "",
    var photo : Int = 0
)