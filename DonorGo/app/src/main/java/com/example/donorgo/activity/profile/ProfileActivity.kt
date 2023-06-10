package com.example.donorgo.activity.profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.login.LoginActivity
import com.example.donorgo.databinding.ActivityProfileBinding
import com.example.donorgo.datastore.SessionPreferences
import com.example.donorgo.datastore.SessionViewModel
import com.example.storyapp.factory.SessionViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private var myToken: String = ""
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()
        // UPLOUD KTP IN HERE To Halaman Uploud KTP
        // Photo Profile juga disini

        auth = Firebase.auth
        firebaseUser = auth.currentUser

        // SessionViewModel
        sessionViewModel.getStateSession().observe(this) { state ->
            if (!state) {
                val moveIntent = Intent(this@ProfileActivity, LoginActivity::class.java)
                moveIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(moveIntent)
                finish()
            }
        }

        sessionViewModel.getUserToken().observe(this) { token ->
            this.myToken = token
            // GET DATA USER
        }

    }

    private fun init() {
        with(binding) {
            homeBtn.setOnClickListener(this@ProfileActivity)
            eventBtn.setOnClickListener(this@ProfileActivity)
            donorBtn.setOnClickListener(this@ProfileActivity)
            newsBtn.setOnClickListener(this@ProfileActivity)
            profileBtn.setOnClickListener(this@ProfileActivity)
            btnHistoryReq.setOnClickListener(this@ProfileActivity)
            btnAboutUs.setOnClickListener(this@ProfileActivity)
            btnContactUs.setOnClickListener(this@ProfileActivity)
            btnLogout.setOnClickListener(this@ProfileActivity)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.home_btn -> {
                startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
            }
            R.id.event_btn -> {}
            R.id.donor_btn -> {}
            R.id.news_btn -> {}
            R.id.profile_btn -> {}
            // BUTTON EDIT PROFILE
            R.id.btn_history_req -> {}
            R.id.btn_about_us -> {}
            R.id.btn_contact_us -> {}
            R.id.btn_logout -> { showAlertDialog() }

        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        val alert = builder.create()
        builder.setTitle(getString(R.string.logout)).setMessage(getString(R.string.logout_confirm))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                logout()
            }.setNegativeButton(getString(R.string.no)) { _, _ ->
                alert.cancel()
            }.show()
    }

    private fun logout() {
        if (firebaseUser != null) { auth.signOut() }
        sessionViewModel.apply {
            saveUsername("")
            saveUserToken("")
            saveUserUniqueID("")
            saveStateSession(false)
        }
        lifecycleScope.launch {
            delay(1000)
            Log.w("LOGIN", "Profile Line 125 token ${sessionViewModel.getUserToken().value}")
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