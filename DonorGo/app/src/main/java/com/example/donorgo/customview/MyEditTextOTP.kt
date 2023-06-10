package com.example.donorgo.customview

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
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

class MyEditTextOTP : RelativeLayout {
    private lateinit var editTextOTP: TextInputEditText
    private lateinit var icErrorOTP: ImageView
    private lateinit var tvErrorOTP: TextView
    var isOTPValid: Boolean = false
    private lateinit var otpLayout: TextInputLayout
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
        inflater.inflate(R.layout.custom_view_otp, this, true)

        otpLayout = findViewById(R.id.otp_layout)
        editTextOTP = findViewById(R.id.edit_text_otp)
        icErrorOTP = findViewById(R.id.ic_error_otp)
        tvErrorOTP = findViewById(R.id.tv_error_otp)

        editTextOTP.addTextChangedListener(otpTextWatcher)
        editTextOTP.setOnFocusChangeListener { _, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkOTP()
        }
    }

    fun getEditTextOTP(): EditText {
        return editTextOTP
    }

    private fun checkOTP() {
        val otp = editTextOTP.text?.trim()
        when {
            otp.isNullOrEmpty() -> {
                isOTPValid = false
                showError(resources.getString(R.string.empty_otp))
            }
            otp.length < 6 -> {
                isOTPValid = false
                showError(resources.getString(R.string.must_be_otp))
            }
            else -> {
                isOTPValid = true
                hideError()
            }
        }
    }

    fun validationOTP() {
        checkOTP()
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextOTP.error = errorMessage
        otpLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.endIconDrawable = null
            it.setEndIconTintList(ContextCompat.getColorStateList(context, R.color.child_red))
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorOTP.apply { this.text = errorMessage }
        icErrorOTP.visibility = View.INVISIBLE

    }

    @SuppressLint("UseCompatLoadingForDrawables", "PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextOTP.error = null
        otpLayout.let {
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
        tvErrorOTP.apply { this.text = resources.getString(R.string.default_required) }
        icErrorOTP.visibility = View.INVISIBLE
    }

    private val otpTextWatcher = object : TextWatcher {
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
            if (isErrorShowing) checkOTP()
        }

        override fun afterTextChanged(s: Editable?) {
            // tidak digunakan
        }
    }

}