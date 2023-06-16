package com.example.donorgo.activity.donor

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
import com.example.donorgo.activity.profile.ProfileViewModel
import com.example.donorgo.databinding.ActivityVoluntaryBinding
import com.example.donorgo.dataclass.RequestBloodDonation
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.helper.DateFormater
import com.example.donorgo.helper.ValidationLastDate
import com.example.storyapp.factory.SessionViewModelFactory

class VoluntaryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityVoluntaryBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val voluntaryViewModel: VoluntaryViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val profileViewModel: ProfileViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myToken: String = ""
    private var userId: String = ""
    private var userAction: Boolean = false
    private var isInputValid: Boolean = false
    private lateinit var userProfileData: UserProfileData
    private var isUserCanDonor: Boolean = false

    private var provSelected: String = ""
    private var citySelected: String = ""
    private var hospitalSelected: String = ""
    private var rsAddress: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoluntaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        catchExtraData()
        // SessionViewModel
        sessionViewModel.getUserToken().observe(this) { token ->
            this.myToken = token
            profileViewModel.getUserProfile(token)
        }
        sessionViewModel.getUserUniqueID().observe(this) { uid ->
            this.userId = uid
            Log.w("uid", uid)
        }

        // ProfileViewModel
        profileViewModel.userProfile.observe(this) {
            this.userProfileData = it
            displayUserProfile(it)
        }

        // RequestViewModel
        voluntaryViewModel.getAllProvince()
        voluntaryViewModel.dataProvince.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val provinceNames = response.map { it.provinsi }

                val adapter =
                    ArrayAdapter(this@VoluntaryActivity, R.layout.list_item_dropdown, provinceNames)
                binding.inputProvince.setAdapter(adapter)
                binding.inputProvince.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        provSelected = adapterView.getItemAtPosition(i).toString()

                        // Dapatkan ID provinsi berdasarkan provinsi yang dipilih
                        val selectedProv = response[i]
                        val selectedProvId = selectedProv.id

                        // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                        voluntaryViewModel.getAllCity(selectedProvId)
                    }
            }

        }
        voluntaryViewModel.dataCity.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val cityNames = response.map { it.city }

                val adapter =
                    ArrayAdapter(this@VoluntaryActivity, R.layout.list_item_dropdown, cityNames)
                binding.inputCity.setAdapter(adapter)
                binding.inputCity.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        citySelected = adapterView.getItemAtPosition(i).toString()

                        // Dapatkan ID kota berdasarkan kota yang dipilih
                        val selectedCity = response[i]
                        val selectedCityId = selectedCity.id

                        // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                        voluntaryViewModel.getAllHospital(selectedCityId)
                    }
            }
        }
        voluntaryViewModel.dataHospital.observe(this) { response ->
            if (response != null && response.isNotEmpty()) {
                val hospitalNames = response.map { it.namaRs }

                val adapter =
                    ArrayAdapter(this@VoluntaryActivity, R.layout.list_item_dropdown, hospitalNames)
                binding.inputHospital.setAdapter(adapter)
                binding.inputHospital.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        hospitalSelected = adapterView.getItemAtPosition(i).toString()
                        rsAddress = response[i].alamatRs
                    }
            }
        }
        voluntaryViewModel.messageDonation.observe(this) { message ->
            if (message != null) voluntaryViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }
        voluntaryViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun catchExtraData(){
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DONOR, UserProfileData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DONOR)
        }
        if (dataParcelable != null){
            this.userProfileData = dataParcelable
            displayUserProfile(userProfileData)
        }
    }

    private fun displayUserProfile(data: UserProfileData) {
        with(binding) {
            username.text = data.name
            lastBloodDonation.text = data.lastDonor?.let { DateFormater.formatDate(it) } ?: "-"
            val canDonateDate = data.lastDonor?.let { DateFormater.countingTheNextThreeMonths(it) } ?: "-"
            canDonateAgain.text = canDonateDate
            tvBloodType.text = data.golDarah
            reshusDar.text = data.rhesus
            isUserCanDonor = data?.lastDonor?.let { ValidationLastDate.isMoreThanThreeMonths(it) } == true
            Log.w("donate", "lastDonor: ${lastBloodDonation.text}")
            Log.w("donate", "canDonateDate: ${canDonateAgain.text}")
            Log.w("donate", "isCan Donate: $isUserCanDonor")

            if (!isUserCanDonor) {
                announcement.visibility = View.VISIBLE
                announcement.text = resources.getString(R.string.can_donate_again, canDonateDate)
                btnDonor.isEnabled = false
            } else {
                announcement.visibility = View.GONE
                announcement.text = resources.getString(R.string.can_donate_again, canDonateDate)
                btnDonor.isEnabled = true
            }
        }
    }

    private fun init() {
        binding.btnDonor.setOnClickListener(this@VoluntaryActivity)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_donor -> {
                userAction = true
                clearFocusAllInput()
                checkFillAllInput()

                if(isInputValid && userId.isNotEmpty()) {
                    Log.w("tes", "$userId")
                    if (!userProfileData.name.isNullOrEmpty()) {
                        val requestBloodDonation = RequestBloodDonation(
                            rsAddress,
                            userId,
                            "-",
                            userProfileData.telp as String,
                            userProfileData.rhesus as String,
                            userProfileData.golDarah as String,
                            userProfileData.lastDonor as String,
                            hospitalSelected,
                            userProfileData.name as String,
                            userProfileData.alamat as String
                        )
                        voluntaryViewModel.postBloodDonation(requestBloodDonation, myToken)
                    }
                }
            }
        }
    }

    private fun clearFocusAllInput() {
        with(binding) {
            inputHospital.clearFocus()
            inputCity.clearFocus()
            inputProvince.clearFocus()
        }
    }

    private fun checkFillAllInput() {
        with(binding) {
            if (provSelected == "") {
                inputProvince.requestFocus()
                showMessage(getString(R.string.province_message), false)
            } else if (citySelected == "") {
                inputCity.requestFocus()
                showMessage(getString(R.string.city_message), false)
            } else if (hospitalSelected == "") {
                inputHospital.requestFocus()
                showMessage(getString(R.string.hospital_message), false)
            } else {
                isInputValid = true
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading && userAction) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            if (userAction) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                if (message == "Blood donor created successfully!") { finish() }
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

    companion object{
        const val EXTRA_DONOR = "extra_donor"
    }

}