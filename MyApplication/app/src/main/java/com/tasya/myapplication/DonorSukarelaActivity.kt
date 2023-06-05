package com.tasya.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.tasya.myapplication.databinding.ActivityMainBinding

class DonorSukarelaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kab = listOf("Bengkalis", "Pekanbaru", "Dumai")
        val kabComplete : AutoCompleteTextView = findViewById(R.id.input_kab)
        val adapter4 = ArrayAdapter(this, R.layout.list_item, kab)
        kabComplete.setAdapter(adapter4)
        kabComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }
    }
}