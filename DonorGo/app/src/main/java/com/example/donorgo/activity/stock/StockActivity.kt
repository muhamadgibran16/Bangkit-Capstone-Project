package com.example.donorgo.activity.stock

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.stock.StockResponse
import com.example.donorgo.activity.stock.ItemStock
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityStockBinding
import com.example.donorgo.retrofit.ApiConfig
import com.example.donorgo.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityStockBinding
    private lateinit var stockAdapter: StockAdapter
    private lateinit var apiService: ApiService

    private val goldar = listOf("A", "B", "AB", "O")

    private var goldarSelected: Int? = null
    private var rhesusSelected: Int? = null

    //    private var positif: Boolean = false
//    private var negatif: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // Inisialisasi ApiService
        apiService = ApiConfig.getApiService()

        // Inisialisasi StockAdapter
        stockAdapter = StockAdapter()

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rv_stock)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@StockActivity)
            adapter = stockAdapter
        }

        // Panggil method untuk mengambil data berita dari ApiService
        fetchStockData()
    }

    private fun fetchStockData() {
        apiService.getAllStock("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLUZHNkRYY1g5YWgiLCJuYW1lIjoiQklMTFkgQlVNQkxFQkVFIFNJRlVMQU5cblxmIiwiZW1haWwiOiJha3V5dXN1ZmZmMTJAZ21haWwuY29tIiwiaWF0IjoxNjg2NjkwODk0LCJleHAiOjE2ODY3NzcyOTR9.hFo6tTaR-2ZeRwbNi-fNHa29fKBDNBK7PhejrwJmSTE")
            .enqueue(object : Callback<StockResponse> {
                override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                    if (response.isSuccessful) {
                        val stockResponse = response.body()
                        val stockItems = stockResponse?.payload

                        stockItems?.let { items ->
                            val stockList = items.mapNotNull { stockItem ->
                                val namaRs = stockItem?.namaRs
                                val alamatRs = stockItem?.alamatRs
                                val tipeDarah = stockItem?.tipeDarah
                                val rhesus = stockItem?.rhesus
                                val bags = stockItem?.stock

                                if (namaRs != null && alamatRs != null && tipeDarah != null && rhesus != null && bags != null) {
                                    ItemStock().apply {
                                        this.namaRs = namaRs
                                        this.alamatRs = alamatRs
                                        this.tipeDarah = tipeDarah
                                        this.rhesus = rhesus
                                        this.stock = bags
                                    }
                                } else {
                                    null
                                }
                            }

                            stockAdapter.setStockList(stockList)
                        }
                    } else {
                        Toast.makeText(
                            this@StockActivity,
                            "Failed to retrieve stock",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d(ContentValues.TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(ContentValues.TAG, "Response dari server: $responseString")
                }

                override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                    Toast.makeText(
                        this@StockActivity,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun fetchStockDataByTypeId() {
        goldarSelected?.let {
            apiService.getStockByTypeId("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLXd6b0toakhZcXciLCJuYW1lIjoiQklMTFkgQlVNQkxFQkVFIFNJRlVMQU5cblxmIiwiZW1haWwiOiJ6ZXJvYWxwaGEwMTAyQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc3NzQxNSwiZXhwIjoxNjg2ODYzODE1fQ.nFuKT0yQYG1gDRGPLG9ud3qyhIABP0vRHoL1lU4uoss",
                it
            )
                .enqueue(object : Callback<StockResponse> {
                    override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                        if (response.isSuccessful) {
                            val stockResponse = response.body()
                            val stockItems = stockResponse?.payload

                            stockItems?.let { items ->
                                val stockList = items.mapNotNull { stockItem ->
                                    val namaRs = stockItem?.namaRs
                                    val alamatRs = stockItem?.alamatRs
                                    val tipeDarah = stockItem?.tipeDarah
                                    val rhesus = stockItem?.rhesus
                                    val bags = stockItem?.stock
                                    val id = stockItem?.idDarah

                                    if (namaRs != null && alamatRs != null && tipeDarah != null && rhesus != null && bags != null && id == goldarSelected) {
                                        // Construct the ItemStock object
                                        ItemStock().apply {
                                            this.namaRs = namaRs
                                            this.alamatRs = alamatRs
                                            this.tipeDarah = tipeDarah
                                            this.rhesus = rhesus
                                            this.stock = bags
                                        }
                                    } else {
                                        null
                                    }
                                }
                                stockAdapter.setStockList(stockList)

                                // Enable the radio buttons
                                binding.positif.isEnabled = true
                                binding.negatif.isEnabled = true
                            }

                        } else {
                            Toast.makeText(
                                this@StockActivity,
                                "Failed to retrieve stock",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Log.d(ContentValues.TAG, "onFailure: ${response.message()}")
                        val responseString = response.errorBody()?.string()
                        Log.d(ContentValues.TAG, "Response dari server: $responseString")
                    }

                    override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                        Toast.makeText(
                            this@StockActivity,
                            t.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    private fun fetchStockDataByTypeIdAndRhesusId() {
        goldarSelected?.let { goldar ->
            rhesusSelected?.let { rhesus ->
                apiService.getStockByTypeIdAndRhesusId(
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLXd6b0toakhZcXciLCJuYW1lIjoiQklMTFkgQlVNQkxFQkVFIFNJRlVMQU5cblxmIiwiZW1haWwiOiJ6ZXJvYWxwaGEwMTAyQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc3NzQxNSwiZXhwIjoxNjg2ODYzODE1fQ.nFuKT0yQYG1gDRGPLG9ud3qyhIABP0vRHoL1lU4uoss",
                    goldar,
                    rhesus
                ).enqueue(object : Callback<StockResponse> {
                    override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                        if (response.isSuccessful) {
                            val stockResponse = response.body()
                            val stockItems = stockResponse?.payload

                            stockItems?.let { items ->
                                val stockList = items.mapNotNull { stockItem ->
                                    val namaRs = stockItem?.namaRs
                                    val alamatRs = stockItem?.alamatRs
                                    val tipeDarah = stockItem?.tipeDarah
                                    val rhesus = stockItem?.rhesus
                                    val bags = stockItem?.stock

                                    // Check if the fetched stock item meets the conditions
                                    if (namaRs != null && alamatRs != null && tipeDarah != null && rhesus != null && bags != null) {
                                        if (goldarSelected == stockItem.idDarah && rhesusSelected == rhesus.toIntOrNull()) {
                                            // Construct the ItemStock object and return it
                                            ItemStock().apply {
                                                this.namaRs = namaRs
                                                this.alamatRs = alamatRs
                                                this.tipeDarah = tipeDarah
                                                this.rhesus = rhesus
                                                this.stock = bags
                                            }
                                        } else {
                                            null // Return null if the conditions are not met
                                        }
                                    } else {
                                        null
                                    }
                                }.toList()


                                stockAdapter.setStockList(stockList)
                            }
                        } else {
                            // Clear the stock list and show a toast message indicating failure
                            stockAdapter.setStockList(emptyList())
                            Toast.makeText(
                                this@StockActivity,
                                "Failed to retrieve stock",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                        // Clear the stock list and show a toast message indicating failure
                        stockAdapter.setStockList(emptyList())
                        Toast.makeText(
                            this@StockActivity,
                            t.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }






    private fun init() {
        with(binding) {

            btBack.setOnClickListener(this@StockActivity)
            // Dropdown Goldar
            val adapter = ArrayAdapter(this@StockActivity, R.layout.list_item_dropdown, goldar)
            inputGoldar.setAdapter(adapter)
            inputGoldar.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, i, _ ->
                    goldarSelected = i + 1
                    fetchStockDataByTypeId()

                }
            // Radio Rhesus
            positif.isEnabled = false
            negatif.isEnabled = false
            rgRhesus.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.positif -> {
                        negatif.isChecked = false
                        rhesusSelected = 1 // 1 = Positif in database
                        fetchStockDataByTypeIdAndRhesusId()
                    }
                    R.id.negatif -> {
                        positif.isChecked = false
                        rhesusSelected = 2 // 2 = Negatif in database
                        fetchStockDataByTypeIdAndRhesusId()
                    }
                }
            }
        }
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_back -> { startActivity(Intent(this@StockActivity, HomeActivity::class.java)) }
        }
    }
}