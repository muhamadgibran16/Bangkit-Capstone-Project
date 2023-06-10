package com.example.donorgo.customview

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.donorgo.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MyEditTextEmail : RelativeLayout {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var icErrorEmail: ImageView
    private lateinit var tvErrorEmail: TextView
    var isEmailValid: Boolean = false
    private lateinit var emailLayout: TextInputLayout
    private var isErrorShowing: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_view_email, this, true)

        emailLayout = findViewById(R.id.email_layout)
        editTextEmail = findViewById(R.id.edit_text_email)
        icErrorEmail = findViewById(R.id.ic_error_email)
        tvErrorEmail = findViewById(R.id.tv_error_email)

        editTextEmail.addTextChangedListener(emailTextWatcher)
        editTextEmail.setOnFocusChangeListener { _, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkEmail()
        }
    }

    fun getEditTextEmail(): EditText {
        return editTextEmail
    }

    private fun checkEmail() {
        val email = editTextEmail.text?.trim()
        when {
            email.isNullOrEmpty() -> {
                isEmailValid = false
                showError(resources.getString(R.string.email_empty))
                Log.w("CHECK", "ERROR")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                isEmailValid = false
                showError(resources.getString(R.string.invalid_email))
            }
            email.length > 30 -> {
                isEmailValid = false
                showError(resources.getString(R.string.input_beyond_max_length))
            }
            else -> {
                isEmailValid = true
                hideError()
            }
        }
    }

    fun validationEmail() {
        checkEmail()
    }

    fun setErrorToEmailField(errorMessage: String) {
        showError(errorMessage)
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextEmail.error = errorMessage
        emailLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.endIconDrawable = null
            it.setEndIconTintList(ContextCompat.getColorStateList(context, R.color.child_red))
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorEmail.apply { this.text = errorMessage }
        icErrorEmail.visibility = View.VISIBLE

    }

    @SuppressLint("UseCompatLoadingForDrawables", "PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextEmail.error = null
        emailLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.pink_soft, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_default_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.black)
            it.endIconDrawable = resources.getDrawable(R.drawable.ic_check, context.theme)
            it.setEndIconTintList(ContextCompat.getColorStateList(context, R.color.pretty_pink))
            it.defaultHintTextColor = ContextCompat.getColorStateList(
                context,
                com.google.android.material.R.color.mtrl_outlined_stroke_color
            )
        }
        tvErrorEmail.apply { this.text = resources.getString(R.string.default_required) }
        icErrorEmail.visibility = View.INVISIBLE
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            // tidak digunakan
        }

        override fun onTextChanged(
            text: CharSequence?,
            start: Int,
            lengthBefore: Int,
            lengthAfter: Int
        ) {
            if (isErrorShowing) checkEmail()
        }

        override fun afterTextChanged(s: Editable?) {
            // tidak digunakan
            checkEmail()
        }
    }

}