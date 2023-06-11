package com.tasya.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class ProvinceResponse(

	@field:SerializedName("payload")
	val payload: List<ProvinceItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ProvinceItem(

	@field:SerializedName("provinsi")
	val provinsi: String,

	@field:SerializedName("id")
	val id: Int
)
