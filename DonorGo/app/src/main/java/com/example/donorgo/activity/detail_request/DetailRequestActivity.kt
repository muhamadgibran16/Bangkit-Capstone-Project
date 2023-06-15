package com.example.donorgo.activity.detail_request

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.databinding.ActivityDetailRequestBinding
import java.util.*

class DetailRequestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailRequestBinding

    private lateinit var recyclerViewSyarat: RecyclerView
    private var mListSyarat = ArrayList<SyaratDetail>()
    private lateinit var adapterSyarat: SyaratDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        recyclerViewSyarat = binding.rvTermsAndConditions

        recyclerViewSyarat.setHasFixedSize(true)
        recyclerViewSyarat.layoutManager = LinearLayoutManager(this)

        mListSyarat.addAll(SyaratDetailData.listData)
        addDataToList()

        adapterSyarat = SyaratDetailAdapter(mListSyarat)
        recyclerViewSyarat.adapter = adapterSyarat
    }

    private fun addDataToList() {
        recyclerViewSyarat.layoutManager = LinearLayoutManager(this)
        adapterSyarat = SyaratDetailAdapter(mListSyarat)
        recyclerViewSyarat.adapter = adapterSyarat
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

    private fun init() {
        with(binding){
            btBack.setOnClickListener(this@DetailRequestActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                startActivity(Intent(this@DetailRequestActivity, HomeActivity::class.java))
            }
        }
    }
}
