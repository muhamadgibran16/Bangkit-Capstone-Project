package com.example.donorgo.activity.request_form

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.otp.OtpActivity
import com.example.donorgo.databinding.ActivityRequestBinding
import com.example.donorgo.dataclass.DataToOTP
import com.example.donorgo.dataclass.RequestBloodRequest
import com.example.donorgo.dataclass.RequestResendOTP
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.storyapp.factory.SessionViewModelFactory

class RequestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRequestBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val requestViewModel: RequestViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val bloodType = listOf("A", "B", "AB", "O")
    private val gender = listOf("Laki - laki ", "Perempuan")
    private var userAction: Boolean = false
    private var isInputValid: Boolean = false
    private var myToken: String = ""

    private var patientname: String = ""
    private var numberOfBloodBags: Int = 0
    private var bloodTypeSelected: String = ""
    private var genderSelected: String = ""
    private var provSelected: String = ""
    private var citySelected: String = ""
    private var rhesusSelected: String = ""
    private var hospitalSelected: String = ""
    private var description: String = ""
    private var familyName: String = ""
    private var phoneNumber: String = ""
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) { token ->
            this.myToken = token
        }
        sessionViewModel.getUserUniqueID().observe(this) { uid ->
            this.userId = uid
        }

        // RequestViewModel
        requestViewModel.getAllProvince()
        requestViewModel.dataProvince.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val provinceNames = response.map { it.provinsi }

                val adapter =
                    ArrayAdapter(this@RequestActivity, R.layout.list_item_dropdown, provinceNames)
                binding.inputProvince.setAdapter(adapter)
                binding.inputProvince.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        provSelected = adapterView.getItemAtPosition(i).toString()

                        // Dapatkan ID provinsi berdasarkan provinsi yang dipilih
                        val selectedProv = response[i]
                        val selectedProvId = selectedProv.id

                        // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                        requestViewModel.getAllCity(selectedProvId)
                    }
            }

        }
        requestViewModel.dataCity.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val cityNames = response.map { it.city }

                val adapter =
                    ArrayAdapter(this@RequestActivity, R.layout.list_item_dropdown, cityNames)
                binding.inputCity.setAdapter(adapter)
                binding.inputCity.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        citySelected = adapterView.getItemAtPosition(i).toString()

                        // Dapatkan ID kota berdasarkan kota yang dipilih
                        val selectedCity = response[i]
                        val selectedCityId = selectedCity.id

                        // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                        requestViewModel.getAllHospital(selectedCityId)
                    }
            }
        }
        requestViewModel.dataHospital.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val hospitalNames = response.map { it.namaRs }

                val adapter =
                    ArrayAdapter(this@RequestActivity, R.layout.list_item_dropdown, hospitalNames)
                binding.inputHospital.setAdapter(adapter)
                binding.inputHospital.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        hospitalSelected = adapterView.getItemAtPosition(i).toString()
                    }
            }
        }
        requestViewModel.messageRequest.observe(this) { message ->
            if (message != null) requestViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }
        requestViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (userAction) {
            binding.progressBar.visibility =
                if (isLoading && userAction) View.VISIBLE else View.GONE
        }
    }

    private fun init() {
        with(binding) {
            btnSend.setOnClickListener(this@RequestActivity)

            // Dropdown Gender
            val adapter = ArrayAdapter(this@RequestActivity, R.layout.list_item_dropdown, gender)
            inputGender.setAdapter(adapter)
            inputGender.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    genderSelected = adapterView.getItemAtPosition(i).toString()
                }

            // Dropdown BloodType
            val adapter2 = ArrayAdapter(this@RequestActivity, R.layout.list_item_dropdown, bloodType)
            inputBloodType.setAdapter(adapter2)
            inputBloodType.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    bloodTypeSelected = adapterView.getItemAtPosition(i).toString()
                }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_send -> {
                userAction = true
                rhesusSelected = if (binding.negatif.isChecked) "-" else "+"
                patientname = binding.inputPatientsName.text?.trim().toString()
                numberOfBloodBags = binding.inputBloodBag.text?.trim().toString().toInt()
                description = binding.inputDescription.text?.trim().toString()
                familyName = binding.inputFamilyName.text?.trim().toString()
                phoneNumber = binding.inputPhone.text?.trim().toString()
                clearFocusAllInput()
                checkFillAllInput()

                if(isInputValid && userId.isNotEmpty()) {
                    Log.w("tes", "$userId")
                    val formRequest = RequestBloodRequest(
                        patientname,
                        numberOfBloodBags,
                        bloodTypeSelected,
                        rhesusSelected,
                        genderSelected,
                        provSelected,
                        citySelected,
                        hospitalSelected,
                        description,
                        familyName,
                        phoneNumber,
                        userId
                    )
                    requestViewModel.postBloodRequest(formRequest, myToken)
                }

            }
        }
    }

    private fun clearFocusAllInput() {
        with(binding) {
            inputHospital.clearFocus()
            inputCity.clearFocus()
            inputProvince.clearFocus()
            inputGender.clearFocus()
            inputBloodType.clearFocus()
            inputBloodBag.clearFocus()
            inputFamilyName.clearFocus()
            inputPatientsName.clearFocus()
            inputPhone.clearFocus()
        }
    }

    private fun checkFillAllInput() {
        with(binding) {
            if (patientname == "" ) {
                inputPatientsName.requestFocus()
                showMessage(getString(R.string.patient_name_message), false)
            } else if (numberOfBloodBags == 0) {
                inputBloodBag.requestFocus()
                showMessage(getString(R.string.blood_bags_message), false)
            } else if (familyName == "") {
                inputFamilyName.requestFocus()
                showMessage(getString(R.string.family_name_message), false)
            } else if (phoneNumber == "") {
                inputPhone.requestFocus()
                showMessage(getString(R.string.phone_number_message), false)
            } else if (genderSelected == "") {
                inputGender.requestFocus()
                showMessage(getString(R.string.gender_message), false)
            } else if (bloodTypeSelected == "") {
                inputBloodType.requestFocus()
                showMessage(getString(R.string.blood_type_message), false)
            } else if (provSelected == "") {
                inputProvince.requestFocus()
                showMessage(getString(R.string.province_message), false)
            } else if (citySelected == "") {
                inputCity.requestFocus()
                showMessage(getString(R.string.city_message), false)
            } else if (hospitalSelected == "") {
                inputHospital.requestFocus()
                showMessage(getString(R.string.hospital_message), false)
            } else if (rhesusSelected == "") {
                showMessage(getString(R.string.rhesus_message), false)
            } else {
                isInputValid = true
            }
        }
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            if (userAction) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                if (message == "Request created successfully!") { finish() }
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
}