package com.example.donorgo.activity.otp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.login.LoginViewModel
import com.example.donorgo.databinding.ActivityOtpBinding
import com.example.donorgo.dataclass.DataToOTP
import com.example.donorgo.dataclass.RequestLogin
import com.example.donorgo.dataclass.RequestResendOTP
import com.example.donorgo.dataclass.RequestVerify
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.storyapp.factory.SessionViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var otpInput: EditText
    private lateinit var userData: DataToOTP
    private var userAction: Boolean = false
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val loginViewModel: LoginViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val otpViewModel: OtpViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var otp: String = ""
    private var userLoginAction: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        sessionViewModel.getStateSession().observe(this) { state ->
            if (state) {
                val moveIntent = Intent(this@OtpActivity, HomeActivity::class.java)
                moveIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(moveIntent)
                finish()
            }
        }

        // Request Access to Login
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        loginViewModel.dataSesion.observe(this) {
            if (loginViewModel.isError.value == false && userAction) {
                sessionViewModel.saveUsername(it.username)
                sessionViewModel.saveUserToken(it.token)
                sessionViewModel.saveUserUniqueID(it.uidUser)
                sessionViewModel.saveStateSession(true)
            }
        }
        loginViewModel.messageLogin.observe(this)  { message ->
            if (message != null) loginViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }

        // Request To OTP API End Point
        otpViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        otpViewModel.messageOTP.observe(this) { message ->
            if (message != null) otpViewModel.isError.value?.let { it1 -> showMessage(message, it1) }
        }

        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
                binding.countdown.text = timeLeftFormatted
            }

            override fun onFinish() {
                binding.countdown.text = getString(R.string.timer_start)
            }
        }

        lifecycleScope.launch {
            delay(DELAY_COUNTDOWN)
            countDownTimer.start()
        }

        // NGAMBIL DATA OTP KIRIM KE CC
        // BIKIN OTP FIELD KAYAK KAK MON
        // NGE-RUN APP NYA KAK MON
        // NGAMBIL TOAST SUCCESS SAMA DIALOG SUCCESS-NYA KAK MON
        // PERGI KE HALAMAN HOME KALOK BERHASIL
        // KALOK GAGAL NAMPILIN
    }

    private fun init() {
        with(binding) {
            countdown.text = "02:00"
            otpInput = layoutOtp.getEditTextOTP()
            resendOtp.setOnClickListener(this@OtpActivity)
            btnVerify.setOnClickListener(this@OtpActivity)
        }
        catchExtraData()
    }

    private fun catchExtraData() {
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA_OTP, DataToOTP::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA_OTP)
        }
        dataParcelable?.let { userData = it }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_verify -> {
                otpInput.clearFocus()
                if (otpInput.text.toString().isEmpty()) binding.layoutOtp.validationOTP()

                if (binding.layoutOtp.isOTPValid) {
                    otp = otpInput.text.toString().trim()
                    userLoginAction = true
                    Log.w("UID", "uid: ${userData.uidUser}")
                    Log.w("UID", "otp: $otp")
                    val dataToVerify = RequestVerify(
                        userData.uidUser,
                        otp
                    )
                    userAction = true
                    otpViewModel.verifyOTP(dataToVerify)
                }
            }
            R.id.resend_otp -> {
                if (userData.uidUser.isNotEmpty() && userData.email.isNotEmpty()) {
                    val dataResendOTP = RequestResendOTP(
                        userData.uidUser,
                        userData.email
                    )
                    userLoginAction = true
                    otpViewModel.resendOTP(dataResendOTP)
                }
                countDownTimer.start()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkAvailabilityData(): Boolean {
        return userData.email.isNotEmpty() && userData.uidUser.isNotEmpty()
    }

    private fun showMessage(message: String, isError: Boolean) {
        if (!isError) {
            if (userLoginAction) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                if (checkAvailabilityData() && message == "Your account verified successfully!") {
                    val credential = RequestLogin(
                        userData.email,
                        userData.pass
                    )
                    loginViewModel.accessLoginUser(credential)
                }
            }
        } else {
            if (userLoginAction) {
                when (message) {
                    "OTP details are not allowed!",
                    "Account record doesn\'t exist or has been verified already, please sign up or login!",
                    "OTP has expired, please request again!",
                    "Invalid OTP, Please check your inbox!",
                    "Empty user details are not allowed!",
                    "Failed to resend OTP!",
                    "No existing OTP record found!" -> {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(
                            this,
                            "${getString(R.string.error_message_tag)} $message",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
        userLoginAction = false
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
        const val EXTRA_DATA_OTP = "extra_dat_otp"
        const val DELAY_COUNTDOWN = 500L
    }

}