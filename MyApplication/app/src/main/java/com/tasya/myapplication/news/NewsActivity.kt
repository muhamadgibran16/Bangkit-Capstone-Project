package com.tasya.myapplication.news

import ApiService

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasya.myapplication.NewsWebViewActivity
import com.tasya.myapplication.R
import com.tasya.myapplication.data.response.NewsItem
import com.tasya.myapplication.data.response.NewsResponse
import com.tasya.myapplication.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
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

        newsAdapter = setListAdapter()

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

        // Panggil method untuk mengambil data berita dari ApiService
        fetchNewsData()
    }

    private fun setListAdapter(): NewsAdapter {
        val listNewsAdapter = NewsAdapter { news: NewsItem ->
            lifecycleScope.launch {
                val moveToNextPage = Intent(this@NewsActivity, NewsWebViewActivity::class.java)
                moveToNextPage.putExtra(NewsWebViewActivity.EXTRA_NEWS, news)
                startActivity(moveToNextPage)
            }
        }
        return listNewsAdapter
    }

    private fun fetchNewsData() {
        apiService.getNews("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLXd6b0toakhZcXciLCJuYW1lIjoiRkVOIENsbFxuXGYiLCJlbWFpbCI6Inplcm9hbHBoYTAxMDJAZ21haWwuY29tIiwiaWF0IjoxNjg2NjY2NTA0LCJleHAiOjE2ODY3NTI5MDR9.ZU5gFltvJKY0J2jVfhoYa8zN1sc2Xd2krnI2jMliImY")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val newsResponse = response.body()
                        val newsList = newsResponse?.payload
                        newsAdapter.setNewsList(newsList as List<NewsItem>)

//                        payloadItems?.let { items ->
//                            val newsList = items.mapNotNull { payloadItem ->
//                                val title = payloadItem?.title
//                                val urlImage = payloadItem?.urlImage
//                                val url = payloadItem?.url
//                                val createdAt = payloadItem?.createdAt
//                                data = pa(
//                                    title,
//                                    url,
//                                    urlImage,
//                                    createdAt
//                                )
//
////                                if (title != null && urlImage != null && url != null) {
////                                    PayloadItem(title = title, urlImage = urlImage, url = url)
////                                } else {
////                                    null
////                                }
////                            }
////
////                            newsAdapter.setNewsList(newsList)
//                        }
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
