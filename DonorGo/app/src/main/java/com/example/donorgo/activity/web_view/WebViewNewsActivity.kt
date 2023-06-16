package com.example.donorgo.activity.web_view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.donorgo.R
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.news.NewsHomeAdapter
import com.example.donorgo.activity.news.News
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.databinding.ActivityWebViewNewsBinding

class WebViewNewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWebViewNewsBinding
    private lateinit var data: News
    private var sourcePage: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        catchExtraData()
        setupView()
        init()

        sourcePage = intent.getStringExtra(SOURCE_PAGE) as String

    }

    private fun init() {
        binding.btBack.setOnClickListener(this)
        with(binding) {
            newsWebView.settings.javaScriptEnabled = true

            newsWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    Toast.makeText(this@WebViewNewsActivity, "Web View berhasil dimuat", Toast.LENGTH_LONG).show()
                }
            }

            newsWebView.loadUrl(data.url)
        }
    }

    private fun catchExtraData(){
        val dataParcelable = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(NEWS_EXTRA_DATA, News::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(NEWS_EXTRA_DATA)
        }
        if (dataParcelable != null) this.data = dataParcelable

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_back -> {
                if (sourcePage == NewsHomeAdapter.HOME_PAGE) {
                    startActivity(Intent(this@WebViewNewsActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@WebViewNewsActivity, NewsActivity::class.java))
                }
            }
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

    companion object {
        const val NEWS_EXTRA_DATA = "news_extra_data"
        const val SOURCE_PAGE = "source_page"
    }

}