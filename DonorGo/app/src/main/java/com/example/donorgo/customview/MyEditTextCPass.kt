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

class MyEditTextCPass : RelativeLayout {
    private lateinit var editTextCPass: TextInputEditText
    private lateinit var icErrorCPass: ImageView
    private lateinit var tvErrorCPass: TextView
    var isCPassValid: Boolean = false
    private lateinit var customPass: MyEditTextPass
    private lateinit var cpassLayout: TextInputLayout
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
        inflater.inflate(R.layout.custom_view_cpass, this, true)

        cpassLayout = findViewById(R.id.cpass_layout)
        editTextCPass = findViewById(R.id.edit_text_cpass)
        icErrorCPass = findViewById(R.id.ic_error_cpass)
        tvErrorCPass = findViewById(R.id.tv_error_cpass)

        editTextCPass.addTextChangedListener(cpasswordTextWatcher)
        editTextCPass.setOnFocusChangeListener { v, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkCPassword()
            // Call listener focus yang ditetapkan di dalam custom view jika ada
            focusChangeListener?.invoke(v, hasFocus)
        }
    }

    fun setOnFocusChangeListener(listener: ((View, Boolean) -> Unit)?) {
        focusChangeListener = listener
    }

    fun getEditTextCPass(): EditText {
        return editTextCPass
    }

    private fun checkCPassword() {
        val cpass = editTextCPass.text.toString().trim()

        when {
            cpass.isEmpty() -> {
                isCPassValid = false
                showError(resources.getString(R.string.cpassword_required))
            }
            !isPasswordMatch(cpass) -> {
                isCPassValid = false
                showError(resources.getString(R.string.cpassword_not_match))
            }
            else -> {
                isCPassValid = true
                hideError()
            }
        }

    }

    fun regisCustomPassword(pass: MyEditTextPass) {
        this.customPass = pass
    }

    fun validationCPass() {
        checkCPassword()
    }

    private fun isPasswordMatch(cpass: String): Boolean {
        return cpass == customPass.getEditTextPass().text.toString().trim()
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextCPass.error = null
        cpassLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorCPass.apply { this.text = errorMessage }
        icErrorCPass.visibility = View.VISIBLE

    }

    @SuppressLint("PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextCPass.error = null
        cpassLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.pink_soft, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_default_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.black)
            it.defaultHintTextColor = ContextCompat.getColorStateList(
                context,
                com.google.android.material.R.color.mtrl_outlined_stroke_color
            )
        }
        tvErrorCPass.apply { this.text = resources.getString(R.string.default_required) }
        icErrorCPass.visibility = View.INVISIBLE
    }

    private val cpasswordTextWatcher = object : TextWatcher {
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
            if (isErrorShowing) checkCPassword()
        }

        override fun afterTextChanged(s: Editable?) {
            validationCPass()
        }
    }

}