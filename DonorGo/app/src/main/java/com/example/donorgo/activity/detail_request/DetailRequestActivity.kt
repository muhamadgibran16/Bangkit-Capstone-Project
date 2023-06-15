package com.example.donorgo.activity.detail_request

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.donor.VoluntaryActivity
import com.example.donorgo.activity.news.News
import com.example.donorgo.activity.news.NewsData
import com.example.donorgo.databinding.ActivityDetailRequestBinding
import com.example.donorgo.dataclass.BloodRequestItem
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.helper.DateFormater
import com.example.donorgo.helper.ValidationLastDate

class DetailRequestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailRequestBinding
    private lateinit var data: BloodRequestItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        catchExtraData()
    }

    private fun catchExtraData(){
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_DATA, BloodRequestItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_DATA)
        }
        if (dataParcelable != null){
            this.data = dataParcelable
            displayUserProfile(data)
        }
    }

    private fun displayUserProfile(data: BloodRequestItem) {
        with(binding) {
            patientName.text = data.namaPasien
            jmlKantong.text = data.jmlKantong.toString()
            goldar.text = data.tipeDarah
            rhesus.text = data.rhesus
            locationValue.text = data.namaRs
            addressValue.text = "-----"
            descriptionValue.text = data.deskripsi
            closeFamilyValue.text = data.namaKeluarga
            phone.text = getString(R.string.number_phone, data.telpKeluarga)

        }
    }

    private fun init () {

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
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
        const val DETAIL_DATA = "detail_data"
    }

}