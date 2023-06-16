package com.example.donorgo.activity.faq

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.databinding.ActivityFaqBinding
import java.util.*

class FaqActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFaqBinding

    private lateinit var recyclerViewFAQ: RecyclerView
    private var mListFAQ = ArrayList<Faq>()
    private lateinit var adapterFAQ: FaqAdapter
    private lateinit var searchViewFAQ: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        recyclerViewFAQ = binding.recyclerViewFAQ
        searchViewFAQ = binding.searchViewFAQ

        recyclerViewFAQ.setHasFixedSize(true)
        recyclerViewFAQ.layoutManager = LinearLayoutManager(this)

        mListFAQ.addAll(FaqData.listData)
        addDataToList()

        adapterFAQ = FaqAdapter(mListFAQ)
        recyclerViewFAQ.adapter = adapterFAQ

        searchViewFAQ.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Faq>()
            for (i in mListFAQ) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapterFAQ.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        recyclerViewFAQ.layoutManager = LinearLayoutManager(this)
        adapterFAQ = FaqAdapter(mListFAQ)
        recyclerViewFAQ.adapter = adapterFAQ
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

    private fun init() {
        with(binding) {
            btBack.setOnClickListener(this@FaqActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                startActivity(Intent(this@FaqActivity, HomeActivity::class.java))
            }
        }
    }
}
