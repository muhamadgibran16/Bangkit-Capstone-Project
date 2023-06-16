package com.example.donorgo.activity.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.event.EventActivity
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var rvNewss: RecyclerView
    private val list = ArrayList<News>()

    //tasyaa

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        rvNewss = binding.rvNews
        rvNewss.setHasFixedSize(true)

        list.addAll(NewsData.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvNewss.layoutManager = LinearLayoutManager(this)
        val academyAdapter = ListNewsAdapter(list)
        rvNewss.adapter = academyAdapter
    }

    private fun init() {
        with(binding) {
            // Button Navigation
            homeBtn.setOnClickListener(this@NewsActivity)
            eventBtn.setOnClickListener(this@NewsActivity)
            listRequestMapsBtn.setOnClickListener(this@NewsActivity)
            newsBtn.setOnClickListener(this@NewsActivity)
            profileBtn.setOnClickListener(this@NewsActivity)

            btBack.setOnClickListener(this@NewsActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // Button Navigation
            R.id.home_btn -> {
                startActivity(Intent(this@NewsActivity, HomeActivity::class.java))
            }
            R.id.event_btn -> {
                startActivity(Intent(this@NewsActivity, EventActivity::class.java))
            }
            R.id.list_request_maps_btn -> {
                startActivity(Intent(this@NewsActivity, MapsRequestActivity::class.java))
            }
            R.id.news_btn -> {}
            R.id.profile_btn -> {
                startActivity(Intent(this@NewsActivity, ProfileActivity::class.java))
            }
            R.id.bt_back -> {
                startActivity(Intent(this@NewsActivity, HomeActivity::class.java))
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

}