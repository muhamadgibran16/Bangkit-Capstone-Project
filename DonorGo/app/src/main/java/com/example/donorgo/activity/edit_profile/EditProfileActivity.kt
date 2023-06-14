package com.example.donorgo.activity.edit_profile

import android.app.DatePickerDialog
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.databinding.ActivityEditProfileBinding
import com.example.donorgo.databinding.ActivityProfileBinding
import com.example.donorgo.dataclass.RequestEditLastDonor
import com.example.donorgo.dataclass.RequestEditUserProfile
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.helper.DateFormater
import com.example.storyapp.factory.SessionViewModelFactory
import java.util.*

class EditProfileActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userProfileData: UserProfileData
    private val bloodType = listOf("A", "B", "AB", "O")
    private val gender = listOf("Male", "Female")
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val editProfileViewModel: EditProfileViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private lateinit var calendar: Calendar
    private var userAction: Boolean = false
    private var isInputValid: Boolean = false
    private var myToken: String = ""
    private var date: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private var dateTTL: String = ""

    private var fiksPhone: String = ""
    private var fiksNIK: String = ""
    private var fiksAddress: String = ""
    private var fiksTTL: String = ""
    private var fiksbloodType: String = ""
    private var fiksrhesus: String = ""
    private var fiksLastDate: String = ""
    private var fiksGender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        catchExtraData()
        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) { this.myToken = it }

        // LastDonorViewModel
        editProfileViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        editProfileViewModel.message.observe(this) { message ->
            if (message != null) editProfileViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }

    }

    private fun catchExtraData(){
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, UserProfileData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }
        if (dataParcelable != null){
            this.userProfileData = dataParcelable
            displayUserProfile(userProfileData)
        }
    }

    private fun displayUserProfile(data: UserProfileData) {
        with(binding) {
            tvNama.text = data.name
            inputNik.text = Editable.Factory.getInstance().newEditable(data.nik ?: "")
            inputPhone.text = Editable.Factory.getInstance().newEditable(data.telp)

            // Split TTL
            val ttlString = data.ttl
            val parts = ttlString?.split(", ")
            val placeBirth = parts?.get(0) ?: ""
            if (parts?.size == 2) {
                val dateBirth = parts?.get(1) ?: ""
                valueBirthDate.text = dateBirth
            }

            inputPlaceBirth.text = Editable.Factory.getInstance().newEditable(placeBirth)
            inputGender.text = Editable.Factory.getInstance().newEditable(data.gender)
            inputGoldar.text = Editable.Factory.getInstance().newEditable(data.golDarah)
            when (data.rhesus) {
                "+" -> positif.isChecked = true
                "-" -> negatif.isChecked = true
                "" -> blank.isChecked = true
            }
            inputAlamat.text = Editable.Factory.getInstance().newEditable(data.alamat ?: "")
            Log.w("goldar", "goldar ${data.golDarah}")
        }
        if (!data.photo.isNullOrEmpty()) {
            setUserPhotoProfile(data.photo, false)
        } else {
            if (data.gender == "Male") setUserPhotoProfile(R.drawable.avatar_cowok.toString(), true)
            if (data.gender == "Female") setUserPhotoProfile(R.drawable.avatar_cewek.toString(), true)
        }
    }

    private fun setUserPhotoProfile(photo: String, isDrawable: Boolean) {
        val placeholder = if (userProfileData.gender == "Male") R.drawable.avatar_cowok else R.drawable.avatar_cewek
        if (isDrawable) {
            val drawablePhoto = photo.toInt()
            Glide.with(this)
                .load(drawablePhoto)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(binding.ivProfile)
        } else {
            Glide.with(this)
                .load(photo)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(binding.ivProfile)
        }
    }

    private fun init() {
        with(binding) {
            binding.btnSave.setOnClickListener(this@EditProfileActivity)
            binding.btnPickBirthDate.setOnClickListener(this@EditProfileActivity)

            // Dropdown Gender
            val adapter = ArrayAdapter(this@EditProfileActivity, R.layout.list_item_dropdown, gender)
            inputGender.setAdapter(adapter)
            inputGender.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    fiksGender = adapterView.getItemAtPosition(i).toString()
                }

            // Dropdown BloodType
            val adapter2 = ArrayAdapter(this@EditProfileActivity, R.layout.list_item_dropdown, bloodType)
            inputGoldar.setAdapter(adapter2)
            inputGoldar.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    fiksbloodType = adapterView.getItemAtPosition(i).toString()
                }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_pickBirthDate -> {
                takeCurrentDate()
                DatePickerDialog(this, this, year, month, date).show()
            }
            R.id.btn_save -> {
                userAction = true
                with(binding) {
                    fiksPhone = inputPhone.text.toString().trim()
                    fiksNIK = inputNik.text.toString().trim()
                    fiksAddress = inputAlamat.text.toString().trim()
                    val place = inputPlaceBirth.text.toString().trim()
                    fiksTTL = "$place, $dateTTL"
                    fiksbloodType = inputGoldar.text.toString().trim()
                    fiksrhesus = when {
                        positif.isChecked -> "+"
                        negatif.isChecked -> "-"
                        else -> ""
                    }
                    fiksLastDate = userProfileData.lastDonor.toString()
                    fiksGender = inputGender.text.toString().trim()
                }
                clearFocusAllInput()
                checkFillAllInput()

                if (isInputValid) {
                    val requestEditUserProfile = RequestEditUserProfile(
                        fiksNIK,
                        fiksPhone,
                        fiksrhesus,
                        fiksGender,
                        fiksbloodType,
                        fiksLastDate,
                        fiksTTL,
                        fiksAddress
                    )
                    editProfileViewModel.editUserProfile(requestEditUserProfile, myToken)
                }
            }
        }
    }

    private fun clearFocusAllInput() {
        with(binding) {
            inputGoldar.clearFocus()
            inputAlamat.clearFocus()
            inputNik.clearFocus()
            inputGender.clearFocus()
            inputPlaceBirth.clearFocus()
            inputPhone.clearFocus()
        }
    }

    private fun checkFillAllInput() {
        with(binding) {
            val checkPlace = binding.inputPlaceBirth.text.toString().trim()
            val checkDate = binding.valueBirthDate.text.toString().trim()
            if (fiksNIK == "") {
                inputNik.requestFocus()
                showMessage(getString(R.string.nik_message_err), false)
            } else if (fiksPhone == "") {
                inputPhone.requestFocus()
                showMessage(getString(R.string.phone_number_message), false)
            } else if (checkPlace == "") {
                inputPlaceBirth.requestFocus()
                showMessage(getString(R.string.province_message), false)
            } else if (fiksGender == "") {
                inputGender.requestFocus()
                showMessage(getString(R.string.gender_message), false)
            } else if (fiksbloodType == "" ) {
                inputGoldar.requestFocus()
                showMessage(getString(R.string.blood_type_message), false)
            } else if (fiksAddress == "") {
                inputAlamat.requestFocus()
                showMessage(getString(R.string.address_message_err), false)
            } else if (checkDate == "-") {
                showMessage(getString(R.string.date_birth_err_message), false)
            } else if (fiksrhesus == "") {
                showMessage(getString(R.string.rhesus_message), false)
            } else {
                isInputValid = true
            }
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
        dateTTL = DateFormater.formatNumberMonthToString(currentDateFormat) as String
        binding.valueBirthDate.text = dateTTL
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
                if (message == "Data updated successfully!") { finish() }
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

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

}