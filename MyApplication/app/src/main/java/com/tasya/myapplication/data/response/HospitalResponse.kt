package com.tasya.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class HospitalResponse(

	@field:SerializedName("payload")
	val payload: List<HospitalItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class HospitalItem(

	@field:SerializedName("alamat_rs")
	val alamatRs: String,

	@field:SerializedName("id_city")
	val idCity: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("nama_rs")
	val namaRs: String,

	@field:SerializedName("telp_rs")
	val telpRs: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("koordinat")
	val koordinat: String,

	@field:SerializedName("longitude")
	val longitude: String
)
