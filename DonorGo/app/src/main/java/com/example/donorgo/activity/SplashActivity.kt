package com.example.donorgo.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.login.LoginActivity
import com.example.donorgo.databinding.ActivitySplashBinding
import com.example.donorgo.datastore.SessionViewModel
import com.example.storyapp.factory.SessionViewModelFactory
import kotlinx.coroutines.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private var alreadyEntered: Boolean = false
    private var isStateTrue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionViewModel.getIsAlreadyEntered().observe(this) { condition ->
            this.alreadyEntered = condition
        }
        sessionViewModel.getStateSession().observe(this) { state ->
            this.isStateTrue = state
        }

        setupView()
        playAnimation()

        // DIALOG BUAT GOL DARAH, RHESUS, LAST DONOR
        lifecycleScope.launch {
            delay(moveActivityDelay)
            if (isStateTrue) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            } else if (alreadyEntered) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, StartedActivity::class.java))
                finish()
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

    private fun playAnimation() {
        val bgSplash = ObjectAnimator.ofFloat(binding.bgSplash, View.ALPHA, 1f).setDuration(bgFadeTimer)
        val appIcon = ObjectAnimator.ofFloat(binding.iconSplash, View.ALPHA, 1f).setDuration(iconFadeTimer)

        AnimatorSet().apply {
            playSequentially(bgSplash, appIcon)
            start()
        }
    }

    companion object {
        private var moveActivityDelay: Long = 1_300L
        private var bgFadeTimer: Long = 300L
        private var iconFadeTimer: Long = 1_000L
    }


}