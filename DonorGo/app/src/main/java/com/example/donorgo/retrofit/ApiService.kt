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

    @Multipart
    @PATCH("upload-ktp/{uid}")
    fun uploudKtp(
        @Path("uid") userId: String,
        @Part file: MultipartBody.Part
    ): Call<ResponseMessage>

//    //Uploud Image DataStory_Schema
//    @Multipart
//    @POST("stories")
//    fun uploudImage(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") latitude: Float? = null,
//        @Part("lon") longitude: Float? = null,
//        @Header("Authorization") token: String
//    ): Call<ResponseMessage>
//
//    //Get All Stories
//    @GET("stories")
//    fun getAllStories(
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int? = 0,
//        @Header("Authorization") token: String
//    ): Call<ResponseGetStory>
//
//    //Get All Stories
//    @GET("stories")
//    suspend fun getAllStoriesWithPagging(
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int? = 0,
//        @Header("Authorization") token: String
//    ): ResponseGetStory

}