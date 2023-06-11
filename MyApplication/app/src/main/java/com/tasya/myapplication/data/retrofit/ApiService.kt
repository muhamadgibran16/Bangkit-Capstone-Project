import android.util.JsonToken
import com.tasya.myapplication.data.BloodRequestRequest
import com.tasya.myapplication.data.response.BloodRequestResponse
import com.tasya.myapplication.data.response.CityResponse
import com.tasya.myapplication.data.response.HospitalResponse
import com.tasya.myapplication.data.response.ProvinceResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("province/city/hospital/{id}")
    fun getHospitalByCityId(@Path("id") idCity: Int): Call<HospitalResponse>

    @GET("province")
    fun getAllProvince(): Call<ProvinceResponse>

    @GET("province/city/{id}")
    fun getCityByProvId(@Path("id") idProv: Int): Call<CityResponse>

    @POST("request")
    fun postBloodRequest(@Body bloodRequestRequest: BloodRequestRequest, @Header ("Authorization") token: String): Call<BloodRequestResponse>
}