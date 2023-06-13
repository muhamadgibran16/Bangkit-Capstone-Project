package com.example.donorgo.activity.faq

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.databinding.ActivityFaqBinding
import java.util.*

class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding

    private lateinit var recyclerViewFAQ: RecyclerView
    private var mListFAQ = ArrayList<Faq>()
    private lateinit var adapterFAQ: FaqAdapter
    private lateinit var searchViewFAQ: androidx.appcompat.widget.SearchView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewFAQ = binding.recyclerViewFAQ
        searchViewFAQ = binding.searchViewFAQ

        recyclerViewFAQ.setHasFixedSize(true)
        recyclerViewFAQ.layoutManager = LinearLayoutManager(this)

        mListFAQ.addAll(FaqData.listData)
        addDataToList()

        adapterFAQ = FaqAdapter(mListFAQ)
        recyclerViewFAQ.adapter = adapterFAQ

        searchViewFAQ.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
        val academyAdapter = FaqAdapter(mListFAQ)
        recyclerViewFAQ.adapter = academyAdapter
    }
}