package com.example.donorgo.activity.lastdonor

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.databinding.ActivityLastDonorBinding
import com.example.donorgo.dataclass.RequestEditLastDonor
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.helper.DateFormater
import com.example.storyapp.factory.SessionViewModelFactory
import java.util.*

class LastDonorActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityLastDonorBinding
    private val bloodType = listOf("A", "B", "AB", "O")
    private val gender = listOf("Male", "Female")
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val lastDonorViewModel: LastDonorViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private lateinit var calendar: Calendar
    private var userAction: Boolean = false
    private var isInputValid: Boolean = false
    private var myToken: String = ""
    private var date: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private var lastDate: String = ""
    private var bloodTypeSelected: String = ""
    private var rhesusSelected: String = "empty"
    private var genderSelected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // SessionViewModel
        sessionViewModel.saveIsOpenFirstDialog(false)
        sessionViewModel.getUserToken().observe(this) { this.myToken = it }

        // LastDonorViewModel
        lastDonorViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        lastDonorViewModel.messageLastDonor.observe(this) { message ->
            if (message != null) lastDonorViewModel.isError?.value?.let { it1 ->
                showMessage(
                    message,
                    it1
                )
            }
        }

    }

    private fun init() {
        with(binding) {
            btnPickLastDate.setOnClickListener(this@LastDonorActivity)
            btnContinue.setOnClickListener(this@LastDonorActivity)

            // Dropdown Gender
            val adapter = ArrayAdapter(this@LastDonorActivity, R.layout.list_item_dropdown, gender)
            inputGender.setAdapter(adapter)
            inputGender.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    genderSelected = adapterView.getItemAtPosition(i).toString()
                }

            // Dropdown BloodType
            val adapter2 =
                ArrayAdapter(this@LastDonorActivity, R.layout.list_item_dropdown, bloodType)
            inputBloodType.setAdapter(adapter2)
            inputBloodType.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    bloodTypeSelected = adapterView.getItemAtPosition(i).toString()
                }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_pick_lastDate -> {
                takeCurrentDate()
                DatePickerDialog(this, this, year, month, date).show()
            }
            R.id.btn_continue -> {
                userAction = true
                rhesusSelected = when {
                    binding.positif.isChecked -> "+"
                    binding.negatif.isChecked -> "-"
                    else -> ""
                }
                binding.inputBloodType.clearFocus()
                binding.inputGender.clearFocus()
                checkFillAllInput()

                if (isInputValid) {
                    val requestEditLastDonor = RequestEditLastDonor(
                        bloodTypeSelected,
                        rhesusSelected,
                        lastDate,
                        genderSelected
                    )
                    lastDonorViewModel.setLastDonor(requestEditLastDonor, myToken)
                }
            }
        }
    }

    private fun checkFillAllInput() {
        if (lastDate == "") {
            showMessage(getString(R.string.lastdate_message), false)
        } else if (bloodTypeSelected == "") {
            binding.inputBloodType.requestFocus()
            showMessage(getString(R.string.blood_type_message), false)
        } else if (rhesusSelected == "empty") {
            showMessage(getString(R.string.rhesus_message), false)
        } else if (genderSelected == "") {
            binding.inputGender.requestFocus()
            showMessage(getString(R.string.gender_message), false)
        } else {
            isInputValid = true
        }
    }

    private fun takeCurrentDate() {
        calendar = Calendar.getInstance()
        date = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.date = dayOfMonth
        val dateToFormat = if (this.date < 10) "0${this.date}" else "${this.date}"
        this.month = month + 1
        val monthToFormat = if (this.month < 10) "0${this.month}" else "${this.date}"
        this.year = year

        val currentDateFormat = "${dateToFormat}-${monthToFormat}-${this.year}"
        val dateFormatToDisplay = DateFormater.formatNumberMonthToString(currentDateFormat)
        binding.valueLastDate.text = dateFormatToDisplay

        this.lastDate =
            dateFormatToDisplay?.let { DateFormater.formatDateToISO(it).toString() } as String
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            // Login Success
            if (userAction) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                if (message == "Data updated successfully!") {
                    finish()
                }
            }
        } else {
            if (userAction) {
                Toast.makeText(
                    this,
                    "${getString(R.string.error_message_tag)} $message",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        userAction = false
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressedDispatcher
        sessionViewModel.saveIsOpenFirstDialog(true)
        finish()
    }

}