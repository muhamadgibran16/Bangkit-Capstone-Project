package com.example.donorgo.activity.event

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var binding: ActivityEventBinding

    private lateinit var rvEvents: RecyclerView
    private val list = ArrayList<Event>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        init()

        rvEvents = binding.rvEvent
        rvEvents.setHasFixedSize(true)

        list.addAll(EventData.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvEvents.layoutManager = LinearLayoutManager(this)
        val academyAdapter = ListEventAdapter(list)
        rvEvents.adapter = academyAdapter
    }

    private fun init() {
        with(binding) {
            btBack.setOnClickListener(this@EventActivity)
        }
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_back -> { startActivity(Intent(this@EventActivity, HomeActivity::class.java)) }
        }
    }
}