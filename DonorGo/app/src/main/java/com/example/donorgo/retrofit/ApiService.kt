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