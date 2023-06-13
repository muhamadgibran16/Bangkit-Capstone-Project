package com.example.donorgo.activity.uploud_photo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.donorgo.R
import com.example.donorgo.activity.camera.SelectImageViewModel
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityHomeBinding
import com.example.donorgo.databinding.ActivityUploudBinding
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.fragment.ChangeFragment
import com.example.donorgo.fragment.PickFragment
import com.example.donorgo.helper.reduceFileImage
import com.example.storyapp.factory.SessionViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploudActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUploudBinding
    private var getFile: File? = null
    private var uploudType: String = ""
    private var myToken: String = ""
    private var userId: String = ""
    private var userAction: Boolean = false
    private lateinit var fragmentManager: FragmentManager
    private lateinit var uploudFragment: PickFragment
    private lateinit var changeFragment: ChangeFragment
    private val selectImageViewModel: SelectImageViewModel by viewModels()
    private val uploudViewModel: UploudViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploudBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) { this.myToken = it }
        sessionViewModel.getUserUniqueID().observe(this) { this.userId = it }

        fragmentManager = supportFragmentManager
        uploudFragment = PickFragment()
        changeFragment = ChangeFragment()

        // SelectImageViewModel
        selectImageViewModel.setConditionOfViewPhoto(false)
        selectImageViewModel.setMyFilePhoto(null)
        selectImageViewModel.isViewPhotoIsFill.observe(this) { setFragmentViewPhoto(it) }
        selectImageViewModel.thisIsFilePhoto.observe(this) { this.getFile = it }

        // UploudViewModel
        uploudViewModel.messageUploud.observe(this) { message ->
            if (message != null) uploudViewModel.isError.value?.let { it1 -> showMessage(message, it1) }
        }
        uploudViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun init() {
        binding.btLetsUploud.setOnClickListener(this)
        uploudType = intent.getStringExtra(UPLOUD_TYPE).toString()
        if (uploudType == ProfileActivity.SCAN_KTP) {
            binding.tvClue.visibility = View.VISIBLE
            binding.ivKtp.visibility = View.VISIBLE
        }
    }

    private fun setFragmentViewPhoto(isFilled: Boolean) {
        val checkFragment = fragmentManager.findFragmentByTag(PickFragment::class.java.simpleName)

        if (isFilled) {
            Log.w("continue", "page 101: ${getFile.toString()}")
            fragmentManager.beginTransaction().apply {
                replace(R.id.layout_fragment, changeFragment, ChangeFragment::class.java.simpleName)
                commit()
            }
        } else {
            if (checkFragment !is PickFragment) {
                Log.w("continue", "whhy")
                fragmentManager.beginTransaction().apply {
                    add(R.id.layout_fragment, uploudFragment, PickFragment::class.java.simpleName)
                    commit()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_lets_uploud -> {
                userAction = true
                when (getFile) {
                    null -> {
                        val text = resources.getString(R.string.empty_image_select)
                        showMessage(text, false)
                    }
                    else -> {
                        showMessage(getString(R.string.compress_and_uploud), false)
                        lifecycleScope.launch {
                            val reducedFile = async { reduceFileImage(getFile as File) }

                            // Menunggu hingga fungsi reduceFileImage selesai
                            val file = reducedFile.await()

                            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                                "image",
                                file.name,
                                requestImageFile
                            )
                            userAction = true
                            Log.w("uploud", "run $uploudType")
                            if (uploudType == ProfileActivity.UPLOUD_PHOTO) uploudViewModel.uploudPhotoProfile(
                                imageMultipart, myToken )
                            if (uploudType == ProfileActivity.SCAN_KTP) uploudViewModel.uploudKTP(
                                userId, imageMultipart )
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading && userAction) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            if (userAction) {
                if (message == "Upload KTP Successfully" ||
                    message == "File uploaded successfully and URL is inserted into the database!") {
                    val customMessage = if (uploudType == ProfileActivity.UPLOUD_PHOTO)  getString(R.string.uploud_photo_succes) else message
                    Toast.makeText(this, customMessage, Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        } else {
            if (userAction) {
                when (message) {
                    "User not found" -> {
                        setMessage(getString(R.string.user_not_found))
                    }
                    "Internal Server Error" -> {
                        if (uploudType == ProfileActivity.SCAN_KTP) setMessage(getString(R.string.invalid_ktp))
                        else setMessage(getString(R.string.internal_server_err))
                    }
                    "timeout" -> {
                        setMessage(getString(R.string.timeout))
                    } else -> {
                        Toast.makeText(
                            this,
                            "${getString(R.string.error_message_tag)} $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        userAction = false
    }

    private fun setMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        )
            .show()
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
        const val UPLOUD_TYPE = "uploud_type"
    }
}