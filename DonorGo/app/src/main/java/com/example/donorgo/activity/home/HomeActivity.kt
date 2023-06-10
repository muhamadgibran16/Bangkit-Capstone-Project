package com.example.donorgo.activity.home

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.donorgo.R
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.activity.dataStore
import com.example.donorgo.databinding.ActivityHomeBinding
import com.example.donorgo.datastore.SessionPreferences
import com.example.donorgo.datastore.SessionViewModel
import com.example.storyapp.factory.SessionViewModelFactory

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private var myUsername: String = ""
    // DONOR BY BLOOD REQUEST && BLOOAD REQUEST LIST
    // REQUEST FORM??,
    // VOLUNTARY DONOR &&

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // SessionViewModel
        sessionViewModel.getUsername().observe(this) {
            this.myUsername = it
            // USERNAME FOR WELCOME AND MAPS
//            binding.tvHello.text = resources.getString(R.string.say_hello, it)
        }


    }

    private fun init() {
        with(binding) {
            homeBtn.setOnClickListener(this@HomeActivity)
            eventBtn.setOnClickListener(this@HomeActivity)
            donorBtn.setOnClickListener(this@HomeActivity)
            newsBtn.setOnClickListener(this@HomeActivity)
            profileBtn.setOnClickListener(this@HomeActivity)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.home_btn -> {}
            R.id.event_btn -> {}
            R.id.donor_btn -> {}
            R.id.news_btn -> {}
            R.id.profile_btn -> {
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
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