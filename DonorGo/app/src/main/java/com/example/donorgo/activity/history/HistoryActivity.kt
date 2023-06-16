package com.example.donorgo.activity.history


import HistoryAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.databinding.ActivityHistoryBinding
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.storyapp.factory.SessionViewModelFactory

class HistoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val historyViewModel: HistoryViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myToken: String = ""
    private var userAction: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // Inisialisasi HistoryAdapter
        historyAdapter = HistoryAdapter()
        // Inisialisasi RecyclerView

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }

        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) {
            this.myToken = it
            Log.w("token123", it)
            historyViewModel.fetchHistoryData(myToken)
        }
        // StockViewModel
        historyViewModel.listHistory.observe(this) { list ->
            if (list != null) historyAdapter.setHistoryList(list)
            Log.w("dataku", list.toString())
        }
        historyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        historyViewModel.messageHistory.observe(this) { message ->
            if (message != null) historyViewModel.isError?.value?.let { it1 ->
                showMessage(
                    message,
                    it1
                )
            }
        }

    }

    private fun init() {
        with(binding) {
            btBack.setOnClickListener(this@HistoryActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                startActivity(Intent(this@HistoryActivity, HomeActivity::class.java))
            }
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
                if (message == "History blood request retrieved successfully!") {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            if (userAction) {
                Toast.makeText(
                    this,
                    "${getString(R.string.error_message_tag)} $message",
                    Toast.LENGTH_LONG
                ).show()
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