package com.tasya.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.tasya.myapplication.data.BloodRequestRequest
import com.tasya.myapplication.data.response.BloodRequestResponse
import com.tasya.myapplication.data.response.CityResponse
import com.tasya.myapplication.data.response.HospitalResponse
import com.tasya.myapplication.data.response.ProvinceResponse
import com.tasya.myapplication.data.retrofit.ApiConfig
import com.tasya.myapplication.databinding.ActivityFormReqBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormReqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormReqBinding

    var namaPasien = ""
    var jmlKantong = 0
    var goldarSelected = ""
    var genderSelected = ""
    var provSelected = ""
    var citySelected = ""
    var rhesusSelected = ""
    var rsSelected = ""
    var desc = ""
    var namaKeluarga = ""
    var nomorHp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormReqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = ApiConfig.getApiService()

        // Inisialisasi adapter untuk dropdown golongan darah
        val goldar = listOf("A", "B", "AB", "O")
        val goldarComplete: AutoCompleteTextView = findViewById(R.id.input_goldar)
        val adapter = ArrayAdapter(this, R.layout.list_item, goldar)
        goldarComplete.setAdapter(adapter)
        goldarComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                goldarSelected = adapterView.getItemAtPosition(i).toString()
                Toast.makeText(this, "Item: $goldarSelected", Toast.LENGTH_SHORT).show()
            }

        // Inisialisasi adapter untuk dropdown jenis kelamin
        val gender = listOf("Laki-laki", "Perempuan")
        val genderComplete: AutoCompleteTextView = findViewById(R.id.input_gender)
        val adapter2 = ArrayAdapter(this, R.layout.list_item, gender)
        genderComplete.setAdapter(adapter2)
        genderComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView2, view, i, l ->
                genderSelected = adapterView2.getItemAtPosition(i).toString()
                Toast.makeText(this, "Item: $genderSelected", Toast.LENGTH_SHORT).show()
            }

        // Inisialisasi adapter untuk dropdown provinsi
        val provComplete: AutoCompleteTextView = findViewById(R.id.input_prov)
        api.getAllProvince().enqueue(object : Callback<ProvinceResponse> {
            override fun onResponse(
                call: Call<ProvinceResponse>,
                response: Response<ProvinceResponse>
            ) {
                if (response.isSuccessful) {
                    val provinceResponse = response.body()
                    val payloadItems = provinceResponse?.payload

                    if (payloadItems != null && payloadItems.isNotEmpty()) {
                        val provinceNames = payloadItems.map { it.provinsi }

                        val adapter =
                            ArrayAdapter(this@FormReqActivity, R.layout.list_item, provinceNames)
                        provComplete.setAdapter(adapter)
                        provComplete.onItemClickListener =
                            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                                provSelected = adapterView.getItemAtPosition(i).toString()
                                Toast.makeText(
                                    this@FormReqActivity,
                                    "Item: $provSelected",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Dapatkan ID provinsi berdasarkan provinsi yang dipilih
                                val selectedProv = payloadItems[i] // Ganti dengan ID provinsi yang ingin Anda pilih
                                val selectedProvId = selectedProv.id

                                // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                                getCityByProvId(selectedProvId)
                            }
                    }
                } else {
                    // Tangani respons gagal
                    Log.e(TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(TAG, "Respons dari server: $responseString")
                }
            }

            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                // Tangani kegagalan koneksi atau permintaan
                Log.e(TAG, "onFail: ${t.message}")
            }
        })

        binding.save.setOnClickListener {
            rhesusSelected = if (binding.negatif.isChecked) "Negatif" else "Positif"
            namaPasien = binding.inputNamaPasien.text?.trim().toString()
            val jml = binding.inputKantong.text?.trim()
            jmlKantong = if (jml.isNullOrBlank()) 0 else jml.toString().toInt()
            desc = binding.inputDesc.text?.trim().toString()
            namaKeluarga = binding.inputDesc.text?.trim().toString()
            nomorHp = binding.inputDesc.text?.trim().toString()

            val formRequest = BloodRequestRequest(
                namaPasien,
                rhesusSelected,
                citySelected,
                genderSelected,
                namaKeluarga,
                rsSelected,
                goldarSelected,
                desc,
                nomorHp,
                jmlKantong,
                provSelected
            )

            postFormRequest(formRequest)
        }
    }

    private fun getCityByProvId(provId: Int) {
        val api = ApiConfig.getApiService()

        api.getCityByProvId(provId).enqueue(object : Callback<CityResponse> {
            override fun onResponse(
                call: Call<CityResponse>,
                response: Response<CityResponse>
            ) {
                if (response.isSuccessful) {
                    val cityResponse = response.body()
                    val payloadItems = cityResponse?.payload

                    if (payloadItems != null && payloadItems.isNotEmpty()) {
                        val filteredCityNames = payloadItems.map { it.city }

                        val adapter =
                            ArrayAdapter(
                                this@FormReqActivity,
                                R.layout.list_item,
                                filteredCityNames
                            )
                        val kabComplete: AutoCompleteTextView = findViewById(R.id.input_kab)
                        kabComplete.setAdapter(adapter)
                        kabComplete.onItemClickListener =
                            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                                citySelected = adapterView.getItemAtPosition(i).toString()
                                Toast.makeText(
                                    this@FormReqActivity,
                                    "Item: $citySelected",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Dapatkan ID kota berdasarkan kota yang dipilih
                                val selectedCity = payloadItems[i] // Ganti dengan ID provinsi yang ingin Anda pilih
                                val selectedCityId = selectedCity.id

                                // Panggil API untuk mendapatkan kota berdasarkan ID provinsi
                                getHospitalByCityId(selectedCityId)
                            }
                    }
                } else {
                    // Tangani respons gagal
                    Log.e(TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(TAG, "Respons dari server: $responseString")
                }
            }

            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                // Tangani kegagalan koneksi atau permintaan
                Log.e(TAG, "onFail: ${t.message}")
            }
        })
    }

    private fun getHospitalByCityId(cityId: Int) {
        val api = ApiConfig.getApiService()

        api.getHospitalByCityId(cityId).enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(
                call: Call<HospitalResponse>,
                response: Response<HospitalResponse>
            ) {
                if (response.isSuccessful) {
                    val hospitalResponse = response.body()
                    val payloadItems = hospitalResponse?.payload

                    if (payloadItems != null && payloadItems.isNotEmpty()) {
                        val filteredRsNames = payloadItems.map { it.namaRs }

                        val adapter =
                            ArrayAdapter(
                                this@FormReqActivity,
                                R.layout.list_item,
                                filteredRsNames
                            )
                        val rsComplete: AutoCompleteTextView = findViewById(R.id.input_rs)
                        rsComplete.setAdapter(adapter)
                        rsComplete.onItemClickListener =
                            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                                rsSelected = adapterView.getItemAtPosition(i).toString()
                                Toast.makeText(
                                    this@FormReqActivity,
                                    "Item: $rsSelected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // Tangani respons gagal
                    Log.e(TAG, "onFailure: ${response.message()}")
                    val responseString = response.errorBody()?.string()
                    Log.d(TAG, "Respons dari server: $responseString")
                }
            }

            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                // Tangani kegagalan koneksi atau permintaan
                Log.e(TAG, "onFail: ${t.message}")
            }
        })
    }

    private fun postFormRequest(request: BloodRequestRequest) {
        val api = ApiConfig.getApiService()
        api.postBloodRequest(
            request,
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1c2VyLXd6b0toakhZcXciLCJuYW1lIjoiTW9sZW4iLCJlbWFpbCI6Inplcm9hbHBoYTAxMDJAZ21haWwuY29tIiwiaWF0IjoxNjg2NDE2NzQzLCJleHAiOjE2ODY1MDMxNDN9.EopsyGrYvKmvGiIkFZgl-mZppporNm-yoo93Pths9to"
        ).enqueue(object : Callback<BloodRequestResponse> {
            override fun onResponse(
                call: Call<BloodRequestResponse>,
                response: Response<BloodRequestResponse>
            ) {
                Toast.makeText(
                    this@FormReqActivity,
                    "${response.body()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<BloodRequestResponse>, t: Throwable) {
                Toast.makeText(
                    this@FormReqActivity,
                    "Failed to make a request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
