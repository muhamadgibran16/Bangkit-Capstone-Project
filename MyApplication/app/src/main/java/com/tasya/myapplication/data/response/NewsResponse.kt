package com.tasya.myapplication.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse(

	@field:SerializedName("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("payload")
	val payload: List<NewsItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Pagination(

	@field:SerializedName("totalItems")
	val totalItems: Int? = null,

	@field:SerializedName("previousLink")
	val previousLink: Any? = null,

	@field:SerializedName("perPage")
	val perPage: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("nextLink")
	val nextLink: Any? = null
)
@Parcelize
data class NewsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("news_id")
	val newsId: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
) : Parcelable
