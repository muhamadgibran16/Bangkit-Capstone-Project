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
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityStockBinding
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.retrofit.ApiConfig
import com.example.donorgo.retrofit.ApiService
import com.example.storyapp.factory.SessionViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityStockBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val stockViewModel: StockViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myToken: String = ""
    private lateinit var stockAdapter: StockAdapter
    private var userAction: Boolean = true

    private val goldar = listOf("A", "B", "AB", "O")

    private var goldarSelected: Int? = null
    private var rhesusSelected: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // Inisialisasi StockAdapter
        stockAdapter = StockAdapter()
        // Inisialisasi RecyclerView
        binding.rvStock.apply {
            layoutManager = LinearLayoutManager(this@StockActivity)
            adapter = stockAdapter
        }

        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) {
            this.myToken = it
            Log.w("token", it)
            stockViewModel.fetchStockAllData(it)
        }

        // StockViewModel
        stockViewModel.listStock.observe(this) { list ->
            stockAdapter.setStockList(list)
        }
        stockViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        stockViewModel.messageStock.observe(this) { message ->
            if (message != null) stockViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
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
                    userAction = true
                    goldarSelected = i + 1
                    if (rhesusSelected != null) {
                        stockViewModel.fetchStockDataByTypeIdAndRhesusId(myToken, goldarSelected as Int, rhesusSelected as Int)
                    } else {
                        stockViewModel.fetchStockDataByTypeId(myToken, goldarSelected as Int)
                    }
                    Log.w("GGG", "b $goldarSelected, r $rgRhesus")
                }

            // Radio Rhesus
            positif.isEnabled = true
            negatif.isEnabled = true
            rgRhesus.setOnCheckedChangeListener { _, checkedId ->
                userAction = true
                when (checkedId) {
                    R.id.positif -> {
                        rhesusSelected = 1 // 1 = Positif in database
                    }
                    R.id.negatif -> {
                        rhesusSelected = 2 // 2 = Negatif in database
                    }
                }

                if (goldarSelected != null) {
                    stockViewModel.fetchStockDataByTypeIdAndRhesusId(myToken, goldarSelected as Int, rhesusSelected as Int)
                } else {
                    stockViewModel.fetchStockAllData(myToken)
                }
                Log.w("GGG", "b $goldarSelected, r $rhesusSelected")
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_back -> { startActivity(Intent(this@StockActivity, HomeActivity::class.java)) }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            if (userAction) {
                binding.rvStock.visibility = View.VISIBLE
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        } else {
            if (userAction) {
                when (message) {
                    "No Stock Available for the Blood Type!" -> binding.rvStock.visibility = View.INVISIBLE
                    else -> {
                        Toast.makeText(
                            this,
                            "${getString(R.string.error_message_tag)} $message",
                            Toast.LENGTH_LONG
                        ).show()
                     }
                }
                Log.w("home", message)
            }
        }
        userAction = false
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