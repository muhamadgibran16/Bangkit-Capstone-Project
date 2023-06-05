package com.tasya.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DetailReqActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<SyaratDetailData>()
    private lateinit var adapter: SyaratDetailAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        //searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = SyaratDetailAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {
        mList.add(
            SyaratDetailData(
                "Syarat Untuk Melakukan Donor Darah",
                R.drawable.ic_donor,
                getString(R.string.syarat_detail)
            )
        )
        mList.add(
            SyaratDetailData(
                "Jangan Melakukan Donor Darah Jika",
                R.drawable.ic_danger,
                getString(R.string.tidak_bisa_donor_darah)
            )
        )
        mList.add(
            SyaratDetailData(
                "Tentang Kemanusiaan",
                R.drawable.ic_give,
                getString(R.string.tentang_kemanusiaan)
            )
        )
    }
}