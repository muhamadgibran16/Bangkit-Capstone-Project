package com.example.donorgo.activity.stock

import com.google.gson.annotations.SerializedName

data class StockResponse(

	@field:SerializedName("payload")
	val payload: List<ItemStock?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ItemStock(

	@field:SerializedName("alamat_rs")
	var alamatRs: String? = null,

	@field:SerializedName("rhesus")
	var rhesus: String? = null,

	@field:SerializedName("nama_rs")
	var namaRs: String? = null,

	@field:SerializedName("tipe_darah")
	var tipeDarah: String? = null,

	@field:SerializedName("stock")
	var stock: Int? = null,

	@field:SerializedName("id_darah")
	val idDarah: Int? = null,

	@field:SerializedName("id_rhesus")
	val idRhesus: Int? = null
)
