package com.example.donorgo.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RequestRegister(

    @field:SerializedName("name")
    var username: String,

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("telp")
    var telp: String,

    @field:SerializedName("password")
    var pass: String,

    @field:SerializedName("confirmPassword")
    var cpass: String

)

data class RequestLogin(

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("password")
    var password: String

)

data class RequestVerify(

    @field:SerializedName("uid")
    var uid: String,

    @field:SerializedName("otp")
    var otp: String

)

data class RequestResendOTP(

    @field:SerializedName("uid")
    var uid: String,

    @field:SerializedName("email")
    var email: String

)

// For ResponseMessage
data class ResponseMessage(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String

)

// For Response Verify OTP
data class ResponseVerifyOTP(

    @field:SerializedName("success")
    var success: Boolean,

    @field:SerializedName("message")
    var message: String

)

// For ResponseMessage Register
data class ResponseMessageRegister(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: DataRegister

)

// For Data Register Login
data class DataRegister(

    @field:SerializedName("uid")
    val uidUser: String,

)

// For ResponseMessage Login
data class ResponseMessageLogin(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: DataLogin

)

// For Data Register Login
data class DataLogin(

    @field:SerializedName("uid")
    val uidUser: String,

    @field:SerializedName("name")
    val username: String,

    @field:SerializedName("token")
    val token: String

)

@Parcelize
data class DataToOTP(
    var email: String,
    var pass: String,
    val uidUser: String
) : Parcelable



