package com.example.donorgo.activity.event

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.donorgo.R
import com.example.donorgo.databinding.ActivityDetailEventBinding


class DetailEventActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        init()

        val imgPhoto = binding.imgItemPhoto
        val tvTitle = binding.tvTitle
        val tvLocation = binding.tvLocation
        val tvDate = binding.tvDate
        val tvStatus = binding.tvStatus
        val tvDetail = binding.tvDetail

        val tPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)
        val tTitle = intent.getStringExtra(EXTRA_TITLE)
        val tLocation = intent.getStringExtra(EXTRA_LOCATION)
        val tDate = intent.getStringExtra(EXTRA_DATE)
        val tStatus = intent.getStringExtra(EXTRA_STATUS)
        val tDetail = intent.getStringExtra(EXTRA_DETAIL)

        val shareButton: ImageButton = binding.btnShare

        tvTitle.text = tTitle

        Glide.with(this)
            .load(tPhoto)
            .apply(RequestOptions())
            .into(imgPhoto)

        tvDetail.text = tDetail
        tvLocation.text = tLocation
        tvStatus.text = tStatus
        tvDate.text = tDate

        shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    intent.getStringExtra(EXTRA_TITLE)
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun init() {
        with(binding) {
            btBack.setOnClickListener(this@DetailEventActivity)
        }
    }

    private fun setupView() {
        binding.tvToolbarTitle.text = intent.getStringExtra(EXTRA_TITLE)

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
        when (v?.id) {
            R.id.bt_back -> {
                startActivity(Intent(this@DetailEventActivity, EventActivity::class.java))
            }
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_LOCATION = "extra_location"
    }
}