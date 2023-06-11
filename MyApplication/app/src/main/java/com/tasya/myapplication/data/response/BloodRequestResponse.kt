package com.tasya.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class BloodRequestResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
