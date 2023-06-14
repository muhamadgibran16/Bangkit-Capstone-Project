package com.example.donorgo.activity.donor

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.donorgo.R
import com.example.donorgo.databinding.ActivityVoluntaryBinding

class VoluntaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVoluntaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoluntaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        binding.btnDonor.setOnClickListener {  }
    }

    private fun init() {

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