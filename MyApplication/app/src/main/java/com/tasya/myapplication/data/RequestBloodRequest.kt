package com.tasya.myapplication.data

import com.google.gson.annotations.SerializedName

data class RequestBloodRequest(

	@field:SerializedName("nama_pasien")
	val namaPasien: String,

	@field:SerializedName("rhesus")
	val rhesus: String,

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("nama_keluarga")
	val namaKeluarga: String,

	@field:SerializedName("nama_rs")
	val namaRs: String,

	@field:SerializedName("tipe_darah")
	val tipeDarah: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("telp_keluarga")
	val telpKeluarga: String,

	@field:SerializedName("jml_kantong")
	val jmlKantong: Int,

	@field:SerializedName("prov")
	val prov: String

)
