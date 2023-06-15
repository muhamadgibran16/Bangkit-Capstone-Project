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
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donorgo.activity.home.HomeActivity
import java.util.*


class DetailRequestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailRequestBinding

    private lateinit var recyclerViewSyarat: RecyclerView
    private var mListSyarat = ArrayList<SyaratDetail>()
    private lateinit var adapterSyarat: SyaratDetailAdapter
    private lateinit var data: BloodRequestItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        catchExtraData()
    }

    private fun catchExtraData() {
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_DATA, BloodRequestItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_DATA)
        }
        if (dataParcelable != null) {
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
            addressValue.text = data.alamatRs
            descriptionValue.text = data.deskripsi
            closeFamilyValue.text = data.namaKeluarga
            phone.text = getString(R.string.number_phone, data.telpKeluarga)

        }
    }

    private fun init() {
        recyclerViewSyarat = binding.rvTermsAndConditions

        recyclerViewSyarat.setHasFixedSize(true)
        recyclerViewSyarat.layoutManager = LinearLayoutManager(this)

        mListSyarat.addAll(SyaratDetailData.listData)
        addDataToList()

        adapterSyarat = SyaratDetailAdapter(mListSyarat)
        recyclerViewSyarat.adapter = adapterSyarat

        with(binding) {
            btBack.setOnClickListener(this@DetailRequestActivity)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                startActivity(Intent(this@DetailRequestActivity, HomeActivity::class.java))
            }
        }
    }

    private fun addDataToList() {
        recyclerViewSyarat.layoutManager = LinearLayoutManager(this)
        adapterSyarat = SyaratDetailAdapter(mListSyarat)
        recyclerViewSyarat.adapter = adapterSyarat
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



