package com.tasya.myapplication.news

import ApiService

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasya.myapplication.R
import com.tasya.myapplication.data.response.NewsResponse
import com.tasya.myapplication.data.response.PayloadItem
import com.tasya.myapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Inisialisasi ApiService
        apiService = ApiConfig.getApiService()

        // Inisialisasi NewsAdapter
        newsAdapter = NewsAdapter()

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

        // Panggil method untuk mengambil data berita dari ApiService
        fetchNewsData()
    }

    private fun fetchNewsData() {
        apiService.getNews("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLXd6b0toakhZcXciLCJuYW1lIjoiPDwgU3JcblxmIiwiZW1haWwiOiJ6ZXJvYWxwaGEwMTAyQGdtYWlsLmNvbSIsImlhdCI6MTY4NjU2OTExMiwiZXhwIjoxNjg2NjU1NTEyfQ.f_AX5a5YpEoU1hdBTe_SqIt2-Cy0GGN2AmCBoEIq48U")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val newsResponse = response.body()
                        val payloadItems = newsResponse?.payload

                        payloadItems?.let { items ->
                            val newsList = items.mapNotNull { payloadItem ->
                                val title = payloadItem?.title
                                val urlImage = payloadItem?.urlImage
                                val url = payloadItem?.url

                                if (title != null && urlImage != null && url != null) {
                                    PayloadItem(title = title, urlImage = urlImage, url = url)
                                } else {
                                    null
                                }
                            }

                            newsAdapter.setNewsList(newsList)
                        }
                    } else {
                        Toast.makeText(
                            this@NewsActivity,
                            "Failed to retrieve news",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d(ContentValues.TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(ContentValues.TAG, "Response dari server: $responseString")
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(
                        this@NewsActivity,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}
