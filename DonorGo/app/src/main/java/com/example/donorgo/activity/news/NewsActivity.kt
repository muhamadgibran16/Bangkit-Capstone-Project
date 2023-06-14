package com.example.donorgo.activity.news

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.donorgo.R
import com.example.donorgo.activity.event.EventActivity
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

    }

    private fun init() {
        with(binding) {
            // Button Navigation
            homeBtn.setOnClickListener(this@NewsActivity)
            eventBtn.setOnClickListener(this@NewsActivity)
            listRequestMapsBtn.setOnClickListener(this@NewsActivity)
            newsBtn.setOnClickListener(this@NewsActivity)
            profileBtn.setOnClickListener(this@NewsActivity)
        }
    }

    override fun onClick(v: View?) {
        // Button Navigation
        when(v?.id) {
            R.id.home_btn -> { startActivity(Intent(this@NewsActivity, HomeActivity::class.java)) }
            R.id.event_btn -> { startActivity(Intent(this@NewsActivity, EventActivity::class.java)) }
            R.id.list_request_maps_btn -> { startActivity(Intent(this@NewsActivity, MapsRequestActivity::class.java)) }
            R.id.news_btn -> {}
            R.id.profile_btn -> { startActivity(Intent(this@NewsActivity, ProfileActivity::class.java)) }
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