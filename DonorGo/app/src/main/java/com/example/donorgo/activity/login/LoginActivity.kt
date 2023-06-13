package com.example.donorgo.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.otp.OtpActivity
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.register.RegisterActivity
import com.example.donorgo.databinding.ActivityLoginBinding
import com.example.donorgo.dataclass.DataToOTP
import com.example.donorgo.dataclass.RequestLogin
import com.example.donorgo.dataclass.RequestResendOTP
import com.example.donorgo.datastore.SessionPreferences
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.storyapp.factory.SessionViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private val loginViewModel: LoginViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private var userAction: Boolean = false
    private var email: String = ""
    private var password: String = ""
    private var userID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // SessionViewModel
        sessionViewModel.getStateSession().observe(this) { state ->
            // LOGIN TO OTP PAGE KALOK BELUM TERFERIFIKASI!!!
            if (state) {
                // FINISHNYA DI OTP
                Log.w("LOGIN", "Login Line 71 token: ${sessionViewModel.getUserToken().value}")
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
            // LOGIN GOOGLE WITHOUT OTP
            // KALOK BELUM SCAN KTP POKOKNYA HARUS SCAN KTP DULU (to Scan KTP)
        }

        // Request Access to Login
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        loginViewModel.dataSesion.observe(this) {
            Log.w("uid", "Login Line 84 uid: $it")
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
        loginViewModel.uniqueID.observe(this) { uid ->
            this.userID = uid
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        auth = Firebase.auth

    }

    private fun init() {
        with(binding) {
            emailInput = layoutEmail.getEditTextEmail()
            passwordInput = layoutPass.getEditTextPass()
            btnLogin.setOnClickListener(this@LoginActivity)
            btnMoveToRegis.setOnClickListener(this@LoginActivity)
            btnGoogle.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                emailInput.clearFocus()
                passwordInput.clearFocus()
                isAllInputEmpty()

                if (isAllDataValid()) {
                    email = emailInput.text.toString().trim()
                    password = passwordInput.text.toString().trim()
                    val credential = RequestLogin(
                        email,
                        password
                    )
                    Log.w("LOGIN", "Login Line 128 $credential")
                    userAction = true
                    loginViewModel.accessLoginUser(credential)
                }
            }
            R.id.btn_google -> {
                googleSignInClient.signOut()
                val signInIntent = googleSignInClient.signInIntent
                resultLauncher.launch(signInIntent)
            }
            R.id.btn_move_to_regis -> {
                val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

    private fun isAllInputEmpty() {
        if (emailInput.text.toString().isEmpty()) binding.layoutEmail.validationEmail()
        if (passwordInput.text.toString().isEmpty()) binding.layoutPass.validationPass()
    }

    private fun isAllDataValid(): Boolean {
        return with(binding) {
            layoutEmail.isEmailValid && layoutPass.isPassValid
        }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            // MASUKKIN TOKEN KE USER PROPIL
            // Telepon, Nama, Email, PASS, CPASS, Token
            // Login Succes, Move to HomePage
            Log.w("LOGIN", "Login Line 207")
            setMessage(getString(R.string.unser_construction))
            // sessionViewModel.saveStateSession(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            // Login Success
            if (userAction) { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
        } else {
            if (userAction) {
                when (message) {
                    "Email not registered!" -> {
                        binding.layoutEmail.setErrorToEmailField(getString(R.string.unauthorized_email))
                        emailInput.requestFocus()
                        setMessage(getString(R.string.unauthorized_email))
                    }
                    "Wrong Password!" -> {
                        binding.layoutPass.setErrorToPassField(getString(R.string.unauthorized_pass))
                        passwordInput.requestFocus()
                        setMessage(getString(R.string.unauthorized_pass))
                    }
                    "Email not verified!" -> {
                        setMessage(getString(R.string.email_not_verified))
                        // Sending to OTP Page
                        val uid = loginViewModel.uniqueID.value
                        uid?.let { RequestResendOTP(it, email) } ?.let { loginViewModel.resendOTP(it) }
                        val intent = Intent(this@LoginActivity, OtpActivity::class.java)
                        val dataIntent = DataToOTP(email, password, userID)
                        intent.putExtra(OtpActivity.EXTRA_DATA_OTP, dataIntent)
                        startActivity(intent)
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
        private const val TAG = "LoginActivity"
    }

}