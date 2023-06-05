package com.tasya.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val goldar = listOf("A", "B", "AB", "O")
        val goldarComplete : AutoCompleteTextView = findViewById(R.id.input_goldar)
        val adapter = ArrayAdapter(this, R.layout.list_item, goldar)
        goldarComplete.setAdapter(adapter)
        goldarComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        val gender = listOf("Laki-laki", "Perempuan")
        val genderComplete: AutoCompleteTextView = findViewById(R.id.input_gender)
        val adapter2 = ArrayAdapter(this, R.layout.list_item, gender)
        genderComplete.setAdapter(adapter2)
        genderComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView2, view, i, l ->
                val itemSelected = adapterView2.getItemAtPosition(i)
                Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
            }

        val prov = listOf("Riau")
        val provComplete: AutoCompleteTextView = findViewById(R.id.input_prov)
        val adapter3 = ArrayAdapter(this, R.layout.list_item, prov)
        provComplete.setAdapter(adapter3)
        provComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView3, view, i, l ->
                val itemSelected = adapterView3.getItemAtPosition(i)
                Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
            }

        val kab = listOf("Bengkalis", "Pekanbaru", "Dumai")
        val kabComplete: AutoCompleteTextView = findViewById(R.id.input_kab)
        val adapter4 = ArrayAdapter(this, R.layout.list_item, kab)
        kabComplete.setAdapter(adapter4)
        kabComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
            }
    }
}