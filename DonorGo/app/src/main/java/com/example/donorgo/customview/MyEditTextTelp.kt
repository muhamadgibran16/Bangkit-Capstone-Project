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

class MyEditTextTelp : RelativeLayout {
    private lateinit var editTextTelp: TextInputEditText
    private lateinit var icErrorTelp: ImageView
    private lateinit var tvErrorTelp: TextView
    var isTelpValid: Boolean = false
    private lateinit var telpLayout: TextInputLayout
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
        inflater.inflate(R.layout.custom_view_telp, this, true)

        telpLayout = findViewById(R.id.telp_layout)
        editTextTelp = findViewById(R.id.edit_text_telp)
        icErrorTelp = findViewById(R.id.ic_error_telp)
        tvErrorTelp = findViewById(R.id.tv_error_telp)

        editTextTelp.addTextChangedListener(telpTextWatcher)
        editTextTelp.setOnFocusChangeListener { _, hasFocus ->
            // Check cpass validity when focus is lost
            if (!hasFocus) checkTelp()
        }
    }

    fun getEditTextTelp(): EditText {
        return editTextTelp
    }

    private fun checkTelp() {
        val telp = editTextTelp.text?.trim()
        when {
            telp.isNullOrEmpty() -> {
                isTelpValid = false
                showError(resources.getString(R.string.telp_empty))
            }
            telp.length <= 9 -> {
                isTelpValid = false
                showError(context.getString(R.string.phone_must_be))
            }
            telp.length > 14 -> {
                isTelpValid = false
                showError(resources.getString(R.string.input_beyond_max_length))
            }
            else -> {
                isTelpValid = true
                hideError()
            }
        }
    }

    fun validationTelp() {
        checkTelp()
    }

    private fun showError(errorMessage: String) {
        isErrorShowing = true
        editTextTelp.error = errorMessage
        telpLayout.let {
            it.boxStrokeColor = resources.getColor(R.color.child_red, context.theme)
            ContextCompat.getColorStateList(context, R.color.outline_error_color)
                ?.let { it1 -> it.setBoxStrokeColorStateList(it1) }
            it.hintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
            it.endIconDrawable = null
            it.setEndIconTintList(ContextCompat.getColorStateList(context, R.color.child_red))
            it.defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.child_red)
        }
        tvErrorTelp.apply { this.text = errorMessage }
        icErrorTelp.visibility = View.VISIBLE

    }

    @SuppressLint("UseCompatLoadingForDrawables", "PrivateResource")
    private fun hideError() {
        isErrorShowing = false
        editTextTelp.error = null
        telpLayout.let {
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
        tvErrorTelp.apply { this.text = resources.getString(R.string.default_required) }
        icErrorTelp.visibility = View.INVISIBLE
    }

    private val telpTextWatcher = object : TextWatcher {
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
            if (isErrorShowing) checkTelp()
        }

        override fun afterTextChanged(s: Editable?) {
            // tidak digunakan
            checkTelp()
        }
    }

}