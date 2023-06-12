package com.tasya.myapplication.news

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        apiService.getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    val payloadItem = newsResponse?.payload

                    payloadItem?.let { items ->
                        val newsList = items.map {
                            PayloadItem(it?.title, it?.urlImage, it?.url)
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
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(
                    this@NewsActivity,
                    "Failed to retrieve news",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}