package com.tasya.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class CityResponse(

	@field:SerializedName("payload")
	val payload: List<CityItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class CityItem(

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_prov")
	val idProv: Int
)
