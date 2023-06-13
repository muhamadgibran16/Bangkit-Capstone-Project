package com.example.donorgo.activity.register

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
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.otp.OtpActivity
import com.example.donorgo.activity.login.LoginActivity
import com.example.donorgo.databinding.ActivityRegisterBinding
import com.example.donorgo.dataclass.DataToOTP
import com.example.donorgo.dataclass.RequestRegister
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

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var cpassInput: EditText
    private var userAction: Boolean = false
    private val registerViewModel: RegisterViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private var username: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var password: String = ""
    private var cpass: String = ""
    private var userID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // Register Account
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        registerViewModel.messageRegis.observe(this) {  message ->
            if (message != null) registerViewModel.isError.value?.let { it1 -> showMessageAndMovePage(message, it1) }
        }
        registerViewModel.uniqueID.observe(this) { uid ->
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
            usernameInput = layoutName.getEditTextUsername()
            emailInput = layoutEmail.getEditTextEmail()
            phoneInput = layoutPhone.getEditTextTelp()
            passwordInput = layoutPass.getEditTextPass()
            cpassInput = layoutCpass.getEditTextCPass()

            layoutCpass.regisCustomPassword(layoutPass)
            layoutPass.regisCustomCPassword(layoutCpass)
            layoutPass.setOnFocusChangeListener { _, _ ->
                if (layoutPass.isPassValid) layoutCpass.validationCPass()
            }
            layoutCpass.setOnFocusChangeListener { _, _ ->
                if (layoutPass.isPassValid) layoutCpass.validationCPass()
            }

            btnSignup.setOnClickListener(this@RegisterActivity)
            btnMoveToLogin.setOnClickListener(this@RegisterActivity)
            btnGoogle.setOnClickListener(this@RegisterActivity)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_signup -> {
                // STEP FOR MOVE TO OTP WHEN BACK BUTTON???
                emailInput.clearFocus()
                usernameInput.clearFocus()
                phoneInput.clearFocus()
                passwordInput.clearFocus()
                cpassInput.clearFocus()
                isAllInputEmpty()

                if (isAllDataValid()) {
                    username = usernameInput.text.toString().trim()
                    email = emailInput.text.toString().trim()
                    phone = phoneInput.text.toString().trim()
                    password = passwordInput.text.toString().trim()
                    cpass = cpassInput.text.toString().trim()
                    val newAccount = RequestRegister(
                        username,
                        email,
                        phone,
                        password,
                        cpass
                    )
                    userAction = true
                    registerViewModel.registerMyNewAccount(newAccount)
                }
            }
            R.id.btn_google -> {
                googleSignInClient.signOut()
                val signInIntent = googleSignInClient.signInIntent
                resultLauncher.launch(signInIntent)
            }
            R.id.btn_move_to_login -> {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isAllInputEmpty() {
        if (usernameInput.text.toString().isEmpty()) binding.layoutName.validationUsername()
        if (emailInput.text.toString().isEmpty()) binding.layoutEmail.validationEmail()
        if (phoneInput.text.toString().isEmpty()) binding.layoutPhone.validationTelp()
        if (passwordInput.text.toString().isEmpty()) binding.layoutPass.validationPass()
        if (cpassInput.text.toString().isEmpty()) binding.layoutCpass.validationCPass()
    }

    private fun isAllDataValid(): Boolean {
        return with(binding) {
            layoutEmail.isEmailValid && layoutName.isUsernameValid && layoutPass.isPassValid && layoutCpass.isCPassValid && layoutPhone.isTelpValid
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
            setMessage(getString(R.string.unser_construction))
            // sessionViewModel.saveStateSession(true)
            // startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
            // finish()
        }
    }

    private fun showMessageAndMovePage(message: String, isError: Boolean) {
        if (!isError) {
            if (userAction) {
                // Regis Success
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, OtpActivity::class.java)
                val dataIntent = DataToOTP(email, password, userID)
                intent.putExtra(OtpActivity.EXTRA_DATA_OTP, dataIntent)
                startActivity(intent)
            }
        } else {
            if (userAction) {
                when (message) {
                    "Email is already exists!" -> {
                        binding.layoutEmail.setErrorToEmailField(getString(R.string.email_already_exists_error))
                        emailInput.requestFocus()
                        setMessage(getString(R.string.email_already_exists))
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
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
        private const val TAG = "RegisterActivity"
    }

}