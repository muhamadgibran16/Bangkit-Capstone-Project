package com.tasya.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tasya.myapplication.data.response.NewsItem
import com.tasya.myapplication.databinding.ActivityNewsWebViewBinding

class NewsWebViewActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNewsWebViewBinding
    private lateinit var data : NewsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = catchExtraData() as NewsItem
        if (data != null) {
            binding.tvToolbarTitle.text = data.title
//          panggil webview
        }
    }

    private fun catchExtraData(): NewsItem? {
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_NEWS, NewsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_NEWS)
        }
        return dataParcelable
    }

    companion object {
        const val EXTRA_NEWS = "Ini Berita"
    }
}