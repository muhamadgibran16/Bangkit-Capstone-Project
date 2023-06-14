package com.example.donorgo.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RequestRegister(

    @field:SerializedName("name")
    var username: String,

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("telp")
    var telp: String,

    @field:SerializedName("password")
    var pass: String,

    @field:SerializedName("confirmPassword")
    var cpass: String

)

data class RequestLogin(

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("password")
    var password: String

)

data class RequestVerify(

    @field:SerializedName("uid")
    var uid: String,

    @field:SerializedName("otp")
    var otp: String

)

data class RequestResendOTP(

    @field:SerializedName("uid")
    var uid: String,

    @field:SerializedName("email")
    var email: String

)

data class RequestBloodRequest(

    @field:SerializedName("nama_pasien")
    val namaPasien: String,

    @field:SerializedName("jml_kantong")
    val jmlKantong: Int,

    @field:SerializedName("tipe_darah")
    val tipeDarah: String,

    @field:SerializedName("rhesus")
    val rhesus: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("prov")
    val prov: String,

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("nama_rs")
    val namaRs: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("nama_keluarga")
    val namaKeluarga: String,

    @field:SerializedName("telp_keluarga")
    val telpKeluarga: String,

    @field:SerializedName("createdBy")
    val userId: String

)

data class RequestEditUserProfile(

    @field:SerializedName("nik")
    val nik: String,

    @field:SerializedName("telp")
    val telp: String,

    @field:SerializedName("rhesus")
    val rhesus: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("gol_darah")
    val golDarah: String,

    @field:SerializedName("last_donor")
    val lastDonor: String,

    @field:SerializedName("ttl")
    val ttl: String,

    @field:SerializedName("alamat")
    val alamat: String

)

data class RequestEditLastDonor(

    @field:SerializedName("gol_darah")
    val golDarah: String,

    @field:SerializedName("rhesus")
    val rhesus: String,

    @field:SerializedName("last_donor")
    val lastDonor: String,

    @field:SerializedName("gender")
    val gender: String

)

data class RequestDonor(

    @field:SerializedName("nama_pasien")
    val namaPasien: String,

    @field:SerializedName("rhesus")
    val rhesus: String,

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("nama_keluarga")
    val namaKeluarga: String,

    @field:SerializedName("createdBy")
    val createdBy: String,

    @field:SerializedName("nama_rs")
    val namaRs: String,

    @field:SerializedName("tipe_darah")
    val tipeDarah: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("telp_keluarga")
    val telpKeluarga: String,

    @field:SerializedName("jml_kantong")
    val jmlKantong: Int,

    @field:SerializedName("prov")
    val prov: String
)

// For ResponseMessage
data class ResponseMessage(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String

)

// For ResponseMessage Register
data class ResponseMessageRegister(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: DataRegister

)

data class DataRegister(

    @field:SerializedName("uid")
    val uidUser: String,

)

// For ResponseMessage Login
data class ResponseMessageLogin(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: DataLogin

)

data class DataLogin(

    @field:SerializedName("uid")
    val uidUser: String,

    @field:SerializedName("name")
    val username: String,

    @field:SerializedName("token")
    val token: String

)

// For Get All Province Response
data class ProvinceResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: List<ProvinceItem>

)

data class ProvinceItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("provinsi")
    val provinsi: String

)

// For Get All City Response
data class CityResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: List<CityItem>

)

data class CityItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("id_prov")
    val idProv: Int,

    @field:SerializedName("city")
    val city: String

)

// For Get All Hospital Response
data class HospitalResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("payload")
    val payload: List<HospitalItem>

)

data class HospitalItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("id_city")
    val idCity: Int,

    @field:SerializedName("nama_rs")
    val namaRs: String,

    @field:SerializedName("alamat_rs")
    val alamatRs: String,

    @field:SerializedName("telp_rs")
    val telpRs: String,

    @field:SerializedName("koordinat")
    val koordinat: String,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("longitude")
    val longitude: String

)

// For Get User Profile
data class UserProfileResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("payload")
    val payload: List<UserProfileData>

)

@Parcelize
data class UserProfileData(

    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("telp")
    val telp: String? = null,

    @field:SerializedName("rhesus")
    val rhesus: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("gol_darah")
    val golDarah: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("last_donor")
    val lastDonor: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("ttl")
    val ttl: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("verified")
    val otp: Boolean = false,

    @field:SerializedName("ktp")
    val ktp: Boolean = false

) : Parcelable

// For Uploud Photo Profile
data class ResponseUploudPhotoProfile(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("url")
    val url: String

)

@Parcelize
data class DataToOTP(
    var email: String,
    var pass: String,
    val uidUser: String
) : Parcelable



