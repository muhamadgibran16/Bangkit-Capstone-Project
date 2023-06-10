package com.tasya.myapplication.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tasya.myapplication.R

class DetailEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        //menambah judul tombol back (kembali ke ListView)
        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra(EXTRA_NAME)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val imgPhoto = findViewById<ImageView>(R.id.img_item_photo)
        val tvName = findViewById<TextView>(R.id.tv_item_name)
        val tvDesc = findViewById<TextView>(R.id.tv_item_description)
        val tvRilis = findViewById<TextView>(R.id.tgl)

        val tPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)
        val tName = intent.getStringExtra(EXTRA_NAME)
        val tDesc = intent.getStringExtra(EXTRA_DESCRIPTION)
        val tRilis = intent.getStringExtra(EXTRA_RILIS)

        val shareButton: ImageButton = findViewById(R.id.btn_share)

        tvName.text = tName

        Glide.with(this)
            .load(tPhoto)
            .apply(RequestOptions())
            .into(imgPhoto)

        tvDesc.text = tDesc
        tvRilis.text = tRilis

        shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    intent.getStringExtra(EXTRA_DESCRIPTION)
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    //perintah untuk tombol back
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DESCRIPTION = "extra_desc"
        const val EXTRA_RILIS = "extra_rilis"

    }
}