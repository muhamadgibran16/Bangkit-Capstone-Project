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

class MyEditTextPass : RelativeLayout {
    private lateinit var editTextPass: TextInputEditText
    private lateinit var icErrorPass: ImageView
    private lateinit var tvErrorPass: TextView
    var isPassValid: Boolean = false
    private var customCPass: MyEditTextCPass? = null
    private lateinit var passLayout: TextInputLayout
    private var isErrorShowing: Boolean = false
    private var focusChangeListener: ((View, Boolean) -> Unit)? = null

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
        inflater.inflate(R.layout.custom_view_pass, this, true)

        passLayout = findViewById(R.id.pass_layout)
        editTextPass = findViewById(R.id.edit_text_pass)
        icErrorPass = findViewById(R.id.ic_error_pass)
        tvErrorPass = findViewById(R.id.tv_error_pass)

        editTextPass.addTextChangedListener(passwordTextWatcher)
        editTextPass.setOnFocusChangeListener { v, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkPassword()
            // Call listener focus yang ditetapkan di dalam custom view jika ada
            focusChangeListener?.invoke(v, hasFocus)
        }
    }

    fun setOnFocusChangeListener(listener: ((View, Boolean) -> Unit)?) {
        focusChangeListener = listener
    }

    fun getEditTextPass(): EditText {
        return editTextPass
    }

    private fun checkPassword() {
        val pass = editTextPass.text?.trim()
        when {
            pass.isNullOrEmpty() -> {
                isPassValid = false
                showError(resources.getString(R.string.pass_empty))
            }
            pass.length < 8 -> {
                isPassValid = false
                showError(resources.getString(R.string.input_under_min_length))
            }
            else -> {
                isPassValid = true
                hideError()
            }
        }
    }

    fun regisCustomCPassword(cpass: MyEditTextCPass) {
        this.customCPass = cpass
    }

    fun validationPass() {
        checkPassword()
    }

    fun setErrorToPassField(errorMessage: String) {
        showError(errorMessage)
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextPass.error = null
        passLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorPass.apply { this.text = errorMessage }
        icErrorPass.visibility = View.VISIBLE

    }

    @SuppressLint("PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextPass.error = null
        passLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.pink_soft, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_default_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.black)
            it.defaultHintTextColor = ContextCompat.getColorStateList(
                context,
                com.google.android.material.R.color.mtrl_outlined_stroke_color
            )
        }
        tvErrorPass.apply { this.text = resources.getString(R.string.default_required) }
        icErrorPass.visibility = View.INVISIBLE
    }

    private val passwordTextWatcher = object : TextWatcher {
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
            if (isErrorShowing) checkPassword()
            if (customCPass != null) {
                if (isPassValid) customCPass?.validationCPass()
            }
        }

        override fun afterTextChanged(s: Editable?) {
            validationPass()
        }
    }

}