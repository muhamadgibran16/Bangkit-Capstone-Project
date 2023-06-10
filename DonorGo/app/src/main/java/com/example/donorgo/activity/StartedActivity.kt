package com.example.donorgo.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.activity.login.LoginActivity
import com.example.donorgo.databinding.ActivityStartedBinding
import com.example.donorgo.datastore.SessionViewModel
import com.example.storyapp.factory.SessionViewModelFactory

class StartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartedBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    // KETIKA ADA DATA YANG NULL PADA SAAT REQUEST API MAKA TAMPILKAN POP UP UNTUK MENGISI DATA DIRI DI EDIT PROFILE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        setupView()

        binding.btnStarted.setOnClickListener {
            sessionViewModel.saveIsAlreadyEntered(true)
            val intent = Intent(this@StartedActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
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

    private fun playAnimation() {
        val bgSplash = ObjectAnimator.ofFloat(binding.startedPage, View.ALPHA, 1f).setDuration(
            bgFadeTimer)
        val btnFade = ObjectAnimator.ofFloat(binding.btnStarted, View.ALPHA, 1f).setDuration(
            btnFadeTimer)
        val btnMove = ObjectAnimator.ofFloat(binding.btnStarted, View.TRANSLATION_Y, -70f, 0f).setDuration(
            btnMoveTimer)

        val btnAnimation = AnimatorSet().apply {
            playTogether(btnFade, btnMove)
        }

        AnimatorSet().apply {
            playSequentially(bgSplash, btnAnimation)
            start()
        }
    }

    companion object {
        private var bgFadeTimer: Long = 300L
        private var btnMoveTimer: Long = 600L
        private var btnFadeTimer: Long = 300L
    }

}