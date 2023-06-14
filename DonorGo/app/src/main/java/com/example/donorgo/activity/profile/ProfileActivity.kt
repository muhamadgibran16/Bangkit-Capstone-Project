package com.example.donorgo.activity.profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.edit_profile.EditProfileActivity
import com.example.donorgo.activity.event.EventActivity
import com.example.donorgo.activity.history.HistoryActivity
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.login.LoginActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.activity.uploud_photo.UploudActivity
import com.example.donorgo.databinding.ActivityProfileBinding
import com.example.donorgo.dataclass.RequestEditUserProfile
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.helper.DateFormater
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
    private val profileViewModel: ProfileViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myToken: String = ""
    private var userAction: Boolean = false
    private lateinit var userProfileData: UserProfileData
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()
        // UPLOUD KTP IN HERE To Halaman Uploud KTP
        // Photo Profile juga disini

        auth = Firebase.auth
        firebaseUser = auth.currentUser
        catchExtraData()

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
            profileViewModel.getUserProfile(myToken)
        }

        // ProfileViewModel
        profileViewModel.userProfile.observe(this) { data ->
            this.userProfileData = data
            displayUserProfile(data)
        }
        profileViewModel.messagePhoto.observe(this) { message ->
            if (message != null) profileViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }
        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        profileViewModel.photoProfile.observe(this) { url ->
            setUserPhotoProfile(url, false)
        }

    }

    private fun catchExtraData(){
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, UserProfileData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }
        if (dataParcelable != null){
            this.userProfileData = dataParcelable
            displayUserProfile(userProfileData)
        }
    }

    private fun displayUserProfile(data: UserProfileData) {
        with(binding) {
            namaUser.text = data.name ?: "-"
            data.name?.let { sessionViewModel.saveUsername(it) }
            phoneUser.text = data.telp ?: "-"
            lastDonorDate.text = data.lastDonor?.let { DateFormater.formatDate(it) } ?: "-"
            canDonoteDate.text = data.lastDonor?.let { DateFormater.countingTheNextThreeMonths(it) } ?: "-"
            typeBlood.text = data.golDarah ?: "-"
            reshusDar.text = data.rhesus ?: ""
            statusVerify.text = if (data.otp && data.ktp) resources.getString(R.string.verified_user)
                                else resources.getString(R.string.unverified_user)
            Log.w("goldar", "goldar ${data.golDarah}")
        }
        if (!data.photo.isNullOrEmpty()) {
            setUserPhotoProfile(data.photo, false)
        } else {
            if (data.gender == "Male") setUserPhotoProfile(R.drawable.avatar_cowok.toString(), true)
            if (data.gender == "Female") setUserPhotoProfile(R.drawable.avatar_cewek.toString(), true)
        }
    }

    private fun setUserPhotoProfile(photo: String, isDrawable: Boolean) {
        val placeholder = if (userProfileData.gender == "Male") R.drawable.avatar_cowok else R.drawable.avatar_cewek
        if (isDrawable) {
            val drawablePhoto = photo.toInt()
            Glide.with(this)
                .load(drawablePhoto)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(binding.imgPhotoProfile)
        } else {
            Glide.with(this)
                .load(photo)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(binding.imgPhotoProfile)
        }
    }

    private fun init() {
        with(binding) {
            // Button Navigation
            homeBtn.setOnClickListener(this@ProfileActivity)
            eventBtn.setOnClickListener(this@ProfileActivity)
            listRequestMapsBtn.setOnClickListener(this@ProfileActivity)
            newsBtn.setOnClickListener(this@ProfileActivity)
            profileBtn.setOnClickListener(this@ProfileActivity)

            // BUTTON PROFILE PAGE
            btnHistoryReq.setOnClickListener(this@ProfileActivity)
            btnAboutUs.setOnClickListener(this@ProfileActivity)
            btnContactUs.setOnClickListener(this@ProfileActivity)
            btnLogout.setOnClickListener(this@ProfileActivity)
            btnSetting.setOnClickListener(this@ProfileActivity)

            // Uploud Photo
            cvProfile.setOnClickListener(this@ProfileActivity)
            btnScanKtp.setOnClickListener(this@ProfileActivity)

            // Edit Profile
            btnEditIdentity.setOnClickListener(this@ProfileActivity)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            // Button Navigation
            R.id.profile_btn -> {}
            R.id.home_btn -> { startActivity(Intent(this@ProfileActivity, HomeActivity::class.java)) }
            R.id.event_btn -> { startActivity(Intent(this@ProfileActivity, EventActivity::class.java)) }
            R.id.list_request_maps_btn -> { startActivity(Intent(this@ProfileActivity, MapsRequestActivity::class.java)) }
            R.id.news_btn -> { startActivity(Intent(this@ProfileActivity, NewsActivity::class.java)) }

            // BUTTON PROFILE PAGE
            R.id.btn_about_us -> {}
            R.id.btn_contact_us -> {}
            R.id.btn_history_req -> { startActivity(Intent(this@ProfileActivity, HistoryActivity::class.java)) }
            // CUSTOM ALERT
            R.id.btn_logout -> { showAlertDialog() }
            // SETTING LANGUAGE
            R.id.btn_setting -> { startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS)) }

            // Uploud Foto
            R.id.cv_profile -> {
                userAction = true
                val intent = Intent(this@ProfileActivity, UploudActivity::class.java)
                intent.putExtra(UploudActivity.UPLOUD_TYPE, UPLOUD_PHOTO)
                startActivity(intent)
            }
            R.id.btn_scan_ktp -> {
                userAction = true
                val intent = Intent(this@ProfileActivity, UploudActivity::class.java)
                intent.putExtra(UploudActivity.UPLOUD_TYPE, SCAN_KTP)
                startActivity(intent)
            }

            // Edit Profile
            R.id.btn_edit_identity -> {
                val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
                intent.putExtra(EditProfileActivity.EXTRA_DATA, userProfileData)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (userAction) {
            binding.progressBar.visibility =
                if (isLoading && userAction) View.VISIBLE else View.GONE
        }
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            // KALOK MESSAGE KTP BERHASIL LANGSUNG VERIFIED USER
            if (userAction && message != "Fetch user data successfully!") { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
            if (message == "Upload KTP Successfully") profileViewModel.getUserProfile(myToken)
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

    companion object {
        const val UPLOUD_PHOTO = "photo_profile"
        const val SCAN_KTP = "scan_ktp"
        const val EXTRA_DATA = "data_user_profile"
    }


}