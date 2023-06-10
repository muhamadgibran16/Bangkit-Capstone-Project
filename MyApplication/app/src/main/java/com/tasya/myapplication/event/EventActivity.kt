package com.tasya.myapplication.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasya.myapplication.R

class EventActivity : AppCompatActivity() {
    private lateinit var rvEvents: RecyclerView
    private val list = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        //menambah judul
        val actionbar = supportActionBar
        actionbar!!.title = "Event Donor Darah"

        rvEvents = findViewById(R.id.rv_event)
        rvEvents.setHasFixedSize(true)

        list.addAll(EventData.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvEvents.layoutManager = LinearLayoutManager(this)
        val academyAdapter = ListEventAdapter(list)
        rvEvents.adapter = academyAdapter
    }
}