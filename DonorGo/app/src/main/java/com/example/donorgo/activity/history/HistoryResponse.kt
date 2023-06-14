package com.example.donorgo.activity.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("payload")
	val payload: List<ItemHistory?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ItemHistory(

	@field:SerializedName("nama_pasien")
	val namaPasien: String? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("telp_keluarga")
	val telpKeluarga: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id_request")
	val idRequest: String? = null,

	@field:SerializedName("rhesus")
	val rhesus: String? = null,

	@field:SerializedName("nama_keluarga")
	val namaKeluarga: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("nama_rs")
	val namaRs: String? = null,

	@field:SerializedName("tipe_darah")
	val tipeDarah: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("jml_kantong")
	val jmlKantong: Int? = null,

	@field:SerializedName("prov")
	val prov: String? = null
)