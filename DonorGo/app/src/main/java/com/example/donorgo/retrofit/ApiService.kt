package com.example.donorgo.retrofit

import com.example.donorgo.dataclass.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //Register Account
    @Headers("Content-Type: application/json")
    @POST("register")
    fun createUser(
        @Body requestRegister: RequestRegister
    ): Call<ResponseMessageRegister>

    //Authentication Account
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body requestLogin: RequestLogin
    ): Call<ResponseMessageLogin>

    //OTP Verification
    @POST("verify-otp")
    fun verifyOTP(
        @Body requestVerify: RequestVerify
    ): Call<ResponseMessage>

    //Resend OTP Code
    @Headers("Content-Type: application/json")
    @POST("send-otp")
    fun resendOTP(
        @Body requestResendOTP: RequestResendOTP
    ): Call<ResponseMessage>

    // Get All Province
    @GET("province")
    fun getAllProvince(): Call<ProvinceResponse>

    // Get All City
    @GET("province/city/{id}")
    fun getCityByProvId(
        @Path("id") idProv: Int
    ): Call<CityResponse>

    // Get All Hospital
    @GET("province/city/hospital/{id}")
    fun getHospitalByCityId(
        @Path("id") idCity: Int
    ): Call<HospitalResponse>

    // Post Blood Request
    @POST("request")
    fun postBloodRequest(
        @Body requestBloodRequest: RequestBloodRequest,
        @Header ("Authorization") token: String
    ): Call<ResponseMessage>

    // Get User Profile
    @GET("users")
    fun getUserProfile(
        @Header ("Authorization") token: String
    ): Call<UserProfileResponse>

    // Edit User Profile
    @PATCH("update-profile")
    fun editUserProfile(
        @Body request: RequestEditUserProfile,
        @Header ("Authorization") token: String
    ): Call<ResponseMessage>

    // First Time Dialog at Home Page
    @PATCH("update-dialog")
    fun setLastDonor(
        @Body request: RequestEditLastDonor,
        @Header ("Authorization") token: String
    ): Call<ResponseMessage>

    // Uploud Photo Profile
    @Multipart
    @PATCH("img-upload")
    fun uploudPhotoProfile(
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<ResponseUploudPhotoProfile>

    // Uploud KTP
    @Multipart
    @PATCH("upload-ktp/{uid}")
    fun uploudKtp(
        @Path("uid") userId: String,
        @Part file: MultipartBody.Part
    ): Call<ResponseMessage>

    //

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @POST("donor")
    fun postBloodDonation(
        @Body requestBloodDonation: RequestBloodDonation,
        @Header("Authorization") token: String
    ): Call<ResponseMessage>

    // All Stock
    @GET("list/all-stock")
    fun getAllStock(
        @Header ("Authorization")token: String
    ): Call<ResponseListStock>

    // List Stock by BloodType
    @GET("list/stock/{id}")
    fun getStockByTypeId(
        @Header ("Authorization") token: String,
        @Path("id") idType: Int
    ): Call<ResponseListStock>

    // List Stick by Blood Type and Rhesus
    @GET("list/stock/type/{typeid}/rhesus/{rhesusid}")
    fun getStockByTypeIdAndRhesusId(
        @Header ("Authorization") token: String,
        @Path("typeid") idBloodType: Int,
        @Path("rhesusid") idRhesus: Int,
    ): Call<ResponseListStock>

    // Get All List Blood Request
    @GET("list/filter-all-data")
    fun getAllBloodRequest(
        @Header ("Authorization") token: String
    ): Call<ResponseListAllBloodRequest>

    // Filter Blood Request by RS Name
    @GET("list/filter-data/{nama_rs}")
    fun getBloodRequestByRSName(
        @Path("nama_rs") namaRs: String,
        @Header ("Authorization")token: String,
    ): Call<ResponseListAllBloodRequest>

    // History Request
    @GET("blood-history")
    fun getAllHistory(
        @Header ("Authorization") token: String
    ): Call<HistoryResponse>


}