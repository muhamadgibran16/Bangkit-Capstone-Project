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

class MyEditTextUser : RelativeLayout {
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var icErrorUsername: ImageView
    private lateinit var tvErrorUsername: TextView
    var isUsernameValid: Boolean = false
    private lateinit var usernameLayout: TextInputLayout
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
        inflater.inflate(R.layout.custom_view_username, this, true)

        usernameLayout = findViewById(R.id.username_layout)
        editTextUsername = findViewById(R.id.edit_text_username)
        icErrorUsername = findViewById(R.id.ic_error_username)
        tvErrorUsername = findViewById(R.id.tv_error_username)

        editTextUsername.addTextChangedListener(usernameTextWatcher)
        editTextUsername.setOnFocusChangeListener { _, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkUsername()
        }
    }

    fun getEditTextUsername(): EditText {
        return editTextUsername
    }

    private fun checkUsername() {
        val username = editTextUsername.text?.trim()
        when {
            username.isNullOrEmpty() -> {
                isUsernameValid = false
                showError(resources.getString(R.string.username_empty))
            }
            username.length > 20 -> {
                isUsernameValid = false
                showError(resources.getString(R.string.input_beyond_max_length))
            }
            else -> {
                isUsernameValid = true
                hideError()
            }
        }
    }

    fun validationUsername() {
        checkUsername()
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextUsername.error = errorMessage
        usernameLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.endIconDrawable = null
            it.setEndIconTintList(ContextCompat.getColorStateList(context, R.color.child_red))
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorUsername.apply { this.text = errorMessage }
        icErrorUsername.visibility = View.VISIBLE

    }

    @SuppressLint("UseCompatLoadingForDrawables", "PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextUsername.error = null
        usernameLayout.let {
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
        tvErrorUsername.apply { this.text = resources.getString(R.string.default_required) }
        icErrorUsername.visibility = View.INVISIBLE
    }

    private val usernameTextWatcher = object : TextWatcher {
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
            if (isErrorShowing) checkUsername()
        }

        override fun afterTextChanged(s: Editable?) {
            // tidak digunakan
            checkUsername()
        }
    }

}