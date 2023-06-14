package com.example.donorgo.activity.history


import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.databinding.ActivityHistoryBinding
import com.example.donorgo.retrofit.ApiConfig
import com.example.donorgo.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var apiService: ApiService

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // Inisialisasi ApiService
        apiService = ApiConfig.getApiService()

        // Inisialisasi HistoryAdapter
        historyAdapter = HistoryAdapter()

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rv_history)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }

        // Panggil method untuk mengambil data berita dari ApiService
        fetchHistoryData()
    }

    private fun init() {

    }

    private fun fetchHistoryData() {
        apiService.getAllHistory("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLUZHNkRYY1g5YWgiLCJuYW1lIjoiQklMTFkgQlVNQkxFQkVFIFNJRlVMQU5cblxmIiwiZW1haWwiOiJha3V5dXN1ZmZmMTJAZ21haWwuY29tIiwiaWF0IjoxNjg2NjkwODk0LCJleHAiOjE2ODY3NzcyOTR9.hFo6tTaR-2ZeRwbNi-fNHa29fKBDNBK7PhejrwJmSTE")
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                    if (response.isSuccessful) {
                        val historyResponse = response.body()
                        val historyItems = historyResponse?.payload

                        historyItems?.let { items ->
                            val historyList = items.mapNotNull { ItemHistory ->
                                val tipeDarah = ItemHistory?.tipeDarah
                                val rhesus = ItemHistory?.rhesus
                                val kota = ItemHistory?.kota
                                val namaPasien = ItemHistory?.namaPasien
                                val namaRs = ItemHistory?.namaRs

                                if (tipeDarah != null && rhesus != null && kota != null && namaPasien != null && namaRs != null) {
                                    ItemHistory(tipeDarah = tipeDarah, rhesus = rhesus, kota = kota, namaPasien = namaPasien, namaRs = namaRs )
                                } else {
                                    null
                                }
                            }

                            historyAdapter.setHistoryList(historyList)
                        }
                    } else {
                        Toast.makeText(
                            this@HistoryActivity,
                            "Failed to retrieve news",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d(ContentValues.TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(ContentValues.TAG, "Response dari server: $responseString")
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    Toast.makeText(
                        this@HistoryActivity,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}