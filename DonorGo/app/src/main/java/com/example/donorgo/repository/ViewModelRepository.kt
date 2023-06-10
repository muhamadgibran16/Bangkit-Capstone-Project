package com.example.donorgo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.donorgo.dataclass.*
import com.example.donorgo.retrofit.ApiConfig
import com.example.donorgo.retrofit.ApiService
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelRepository constructor(
    private val apiService: ApiService,
) {
//    private val result = MediatorLiveData<Result<List<TabelStory>>>()

    private val _dataSesion = MutableLiveData<DataLogin>()
    val dataSesion: LiveData<DataLogin> = _dataSesion

    private val _uniqueID = MutableLiveData<String>()
    val uniqueID: LiveData<String> = _uniqueID

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

    companion object {
        @Volatile
        private var instance: ViewModelRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): ViewModelRepository =
            instance ?: synchronized(this) {
                instance ?: ViewModelRepository(apiService)
            }.also { instance = it }
    }
}