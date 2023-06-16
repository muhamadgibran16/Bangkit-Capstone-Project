package com.example.donorgo.repository

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.donorgo.BuildConfig
import com.example.donorgo.database.LocalRoomDatabase
import com.example.donorgo.database.TabelBloodRequest
import com.example.donorgo.dataclass.*
import com.example.donorgo.helper.AppExecutors
import com.example.donorgo.retrofit.ApiConfig
import com.example.donorgo.retrofit.ApiService
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.donorgo.helper.Result

class ViewModelRepository constructor(
    private val apiService: ApiService,
    private val database: LocalRoomDatabase,
    private val appExecutors: AppExecutors,
) {
    private val result = MediatorLiveData<Result<List<TabelBloodRequest>>>()

    private val _dataSesion = MutableLiveData<DataLogin>()
    val dataSesion: LiveData<DataLogin> = _dataSesion

    private val _uniqueID = MutableLiveData<String>()
    val uniqueID: LiveData<String> = _uniqueID

    private val _dataProvince = MutableLiveData<List<ProvinceItem>>()
    val dataProvince: LiveData<List<ProvinceItem>> = _dataProvince

    private val _dataCity = MutableLiveData<List<CityItem>>()
    val dataCity: LiveData<List<CityItem>> = _dataCity

    private val _dataHospital = MutableLiveData<List<HospitalItem>>()
    val dataHospital: LiveData<List<HospitalItem>> = _dataHospital

    private val _userProfile = MutableLiveData<UserProfileData>()
    val userProfile: LiveData<UserProfileData> = _userProfile

    private val _photoProfile = MutableLiveData<String>()
    val photoProfile: LiveData<String> = _photoProfile

    private val _listRequest = MutableLiveData<List<BloodRequestItem>>()
    val listRequest: LiveData<List<BloodRequestItem>> = _listRequest

    private val _listStock = MutableLiveData<List<StockItem>>()
    val listStock: LiveData<List<StockItem>> = _listStock

    private val _listHistory = MutableLiveData<List<ItemHistory>?>()
    val listHistory: LiveData<List<ItemHistory>?> = _listHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    // When New Account Register
    fun registerMyNewAccount(newAccount: RequestRegister) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().createUser(newAccount)
        client.enqueue(object : Callback<ResponseMessageRegister> {
            override fun onResponse(
                call: Call<ResponseMessageRegister>,
                response: Response<ResponseMessageRegister>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _uniqueID.value = data?.payload?.uidUser
                    _message.value = data?.message
                } else {
                    if (response.code() == 409) {
                        val errorBody = response.errorBody()?.string()
                        try {
                            val json = errorBody?.let { JSONObject(it) }
                            val errorMessage = json?.getString("message")
                            _message.value = errorMessage
                        } catch (e: JSONException) {
                            _message.value = e.message.toString()
                        }
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMessageRegister>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    // When User Login
    fun accessLoginUser(credential: RequestLogin) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(credential)
        client.enqueue(object : Callback<ResponseMessageLogin> {
            override fun onResponse(
                call: Call<ResponseMessageLogin>,
                response: Response<ResponseMessageLogin>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                Log.w("check", "viewModel ${_isError.value}")
                if (response.isSuccessful) {
                    val data = response.body()
                    _dataSesion.value = data?.payload
                    _message.value = data?.message
                } else {
                    if (response.code() == 401 || response.code() == 403) {
                        val errorBody = response.errorBody()?.string()
                        try {
                            val json = errorBody?.let { JSONObject(it) }
                            val errorMessage = json?.getString("message")
                            if (errorMessage == "Email not verified!") {
                                val payloadJson = json?.optJSONObject("payload")
                                // UID NYA JALA GAK NIH APA ERROR
                                val uid = payloadJson?.getString("uid")
                                uid?.let { _uniqueID.value = it }
                            }
                            _message.value = errorMessage
                        } catch (e: JSONException) {
                            _message.value = e.message.toString()
                        }
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMessageLogin>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun verifyOTP(dataToVerify: RequestVerify) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().verifyOTP(dataToVerify)
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.w("UID", "repo -> uid: ${dataToVerify.uid} otp: ${dataToVerify.otp}")
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                } else {
                    if (response.code() == 400 || response.code() == 403) {
                        val errorBody = response.errorBody()?.string()
                        try {
                            val json = errorBody?.let { JSONObject(it) }
                            val errorMessage = json?.getString("message")
                            _message.value = errorMessage
                        } catch (e: JSONException) {
                            _message.value = e.message.toString()
                        }
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun resendOTP(requestResendOTP: RequestResendOTP) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().resendOTP(requestResendOTP)
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                } else {
                    if (response.code() == 400 || response.code() == 500) {
                        val errorBody = response.errorBody()?.string()
                        try {
                            val json = errorBody?.let { JSONObject(it) }
                            val errorMessage = json?.getString("message")
                            _message.value = errorMessage
                        } catch (e: JSONException) {
                            _message.value = e.message.toString()
                        }
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun getAllProvince() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllProvince()
        client.enqueue(object : Callback<ProvinceResponse> {
            override fun onResponse(
                call: Call<ProvinceResponse>,
                response: Response<ProvinceResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _dataProvince.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun getAllCity(provId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCityByProvId(provId)
        client.enqueue(object : Callback<CityResponse> {
            override fun onResponse(
                call: Call<CityResponse>,
                response: Response<CityResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _dataCity.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun getAllHospital(cityId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getHospitalByCityId(cityId)
        client.enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(
                call: Call<HospitalResponse>,
                response: Response<HospitalResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _dataHospital.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun postBloodRequest(request: RequestBloodRequest, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postBloodRequest(request, "Bearer $token")
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun getDataUserProfile(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserProfile("Bearer $token")
        client.enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = "Fetch user data successfully!"
                    _userProfile.value = data?.payload?.get(0)
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun editUserProfile(request: RequestEditUserProfile, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().editUserProfile(request, "Bearer $token")
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun setFirstLastDonor(request: RequestEditLastDonor, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().setLastDonor(request, "Bearer $token")
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    Log.w("z", "giuiug")
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun uploudPhotoProfile(image: MultipartBody.Part, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().uploudPhotoProfile(image, "Bearer $token")
        client.enqueue(object : Callback<ResponseUploudPhotoProfile> {
            override fun onResponse(
                call: Call<ResponseUploudPhotoProfile>,
                response: Response<ResponseUploudPhotoProfile>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                Log.w("uploud", "sukses: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _photoProfile.value = data?.url
                    Log.w("uploud", "photo berhasil")
                } else {
                    _message.value = response.message()
                }
                Log.w("uploud", "code: ${response.code()}, message: ${response.body()?.message}")
            }

            override fun onFailure(call: Call<ResponseUploudPhotoProfile>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun uploudKTP(userId: String, image: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(BuildConfig.OCR_URL).uploudKtp(userId, image)
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                Log.w("uploud", "sukses: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    Log.w("uploud", "ktp berhasil")
                } else {
                    if (response.code() == 404) {
                        val errorBody = response.errorBody()?.string()
                        try {
                            val json = errorBody?.let { JSONObject(it) }
                            val errorMessage = json?.getString("message")
                            _message.value = errorMessage
                        } catch (e: JSONException) {
                            _message.value = e.message.toString()
                        }
                    } else if (response.code() == 500) {
                        _message.value = "Internal Server Error"
                    } else {
                        _message.value = response.message()
                        Log.w("uploud", "hmmm ${response.code()}")
                    }
                }
                Log.w("uploud", "code: ${response.code()}, message: ${response.body()?.message}")
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
                Log.w("uploud", "ktp gagal2")
            }
        })
    }

    fun postBloodDonation(request: RequestBloodDonation, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postBloodDonation(request, "Bearer $token")
        client.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun getAllBloodRequest(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllBloodRequest("Bearer $token")
        client.enqueue(object : Callback<ResponseListAllBloodRequest> {
            override fun onResponse(
                call: Call<ResponseListAllBloodRequest>,
                response: Response<ResponseListAllBloodRequest>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                Log.w("home", "na $response.isSuccessful")
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _listRequest.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseListAllBloodRequest>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
                Log.w("home", "hehehhe")
            }

        })
    }

    // To get Story User From Database
    fun getTabelBloodRequestFromDB(): LiveData<Int> = database.requestBloodDao().getTabelRequestSize()

    fun getAllBloodRequestIntoDB(token: String): MediatorLiveData<Result<List<TabelBloodRequest>>> {
        result.value = Result.Loading
        if (token.isNotEmpty()) {
            val client = apiService.getAllBloodRequest("Bearer $token")
            client.enqueue(object : Callback<ResponseListAllBloodRequest> {
                override fun onResponse(
                    call: Call<ResponseListAllBloodRequest>,
                    response: Response<ResponseListAllBloodRequest>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        val myRequest = data?.payload
                        val convertList = ArrayList<TabelBloodRequest>()
                        appExecutors.diskIO.execute {
                            myRequest?.forEach { request ->
                                val rawData = TabelBloodRequest(
                                    request.idRequest,
                                    request.alamatRs,
                                    request.namaPasien,
                                    request.kota,
                                    request.gender,
                                    request.latitude,
                                    request.telpKeluarga,
                                    request.createdAt,
                                    request.rhesus,
                                    request.namaKeluarga,
                                    request.namaRs,
                                    request.tipeDarah,
                                    request.deskripsi,
                                    request.jmlKantong,
                                    request.prov,
                                    request.longitude
                                )
                                convertList.add(rawData)
                            }
                            if (myRequest != null) {
                                database.requestBloodDao().deleteAllData()
                                database.requestBloodDao().insertUpToDateData(convertList)
                            }
                        }
                    } else {
                        result.value = Result.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseListAllBloodRequest>, t: Throwable) {
                    result.value = Result.Error(t.message.toString())
                }
            })
            val localData = database.requestBloodDao().getAllRequestFromDB()
            result.addSource(localData) { newData: List<TabelBloodRequest> ->
                result.value = Result.Success(newData)
            }
        }
        return result
    }

    fun fetchStockAllData(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllStock("Bearer $token")
        client.enqueue(object : Callback<ResponseListStock> {
            override fun onResponse(
                call: Call<ResponseListStock>,
                response: Response<ResponseListStock>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _listStock.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseListStock>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun fetchStockDataByTypeId(token: String, idBloodType: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStockByTypeId("Bearer $token", idBloodType)
        client.enqueue(object : Callback<ResponseListStock> {
            override fun onResponse(
                call: Call<ResponseListStock>,
                response: Response<ResponseListStock>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _listStock.value = data?.payload
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseListStock>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    fun fetchStockDataByTypeIdAndRhesusId(token: String, idBloodType: Int, idRhesus: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStockByTypeIdAndRhesusId("Bearer $token", idBloodType, idRhesus)
        client.enqueue(object : Callback<ResponseListStock> {
            override fun onResponse(
                call: Call<ResponseListStock>,
                response: Response<ResponseListStock>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _listStock.value = data?.payload
                    Log.w("GGG", "1")
                } else {
                    if (response.code() == 404) {
                        Log.w("GGG", "hhh")
                        _message.value = "No Stock Available for the Blood Type!"
                    } else if (response.code() == 500) {
                        _message.value = "Internal Server Error"
                    } else {
                        _message.value = response.message()
                        Log.w("uploud", "hmmm ${response.code()}")
                    }
                    Log.w("GGG", "2")
                }
            }

            override fun onFailure(call: Call<ResponseListStock>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
                Log.w("GGG", "3")
            }
        })
    }

   fun fetchHistoryData(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllHistory("Bearer $token")
        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
                if (response.isSuccessful) {
                    val data = response.body()
                    _message.value = data?.message
                    _listHistory.value = data?.payload
                    Log.w("GGG", "1")
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                _message.value = t.message.toString()
                Log.w("GGG", "3")
            }
        })

    }

    companion object {
        @Volatile
        private var instance: ViewModelRepository? = null

        fun getInstance(
            apiService: ApiService,
            database: LocalRoomDatabase,
            appExecutors: AppExecutors
        ): ViewModelRepository =
            instance ?: synchronized(this) {
                instance ?: ViewModelRepository(apiService, database, appExecutors)
            }.also { instance = it }
    }
}