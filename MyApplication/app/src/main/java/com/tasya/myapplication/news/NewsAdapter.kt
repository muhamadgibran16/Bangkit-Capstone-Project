package com.tasya.myapplication.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasya.myapplication.data.response.PayloadItem
import com.tasya.myapplication.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val newsList: MutableList<PayloadItem> = mutableListOf()

    fun setNewsList(news: List<PayloadItem>) {
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: PayloadItem) {
            Log.d("NewsAdapter", "Title: ${news.title}")
            Log.d("NewsAdapter", "Title: ${news.url}")
            binding.news = news // Mengatur objek news sebagai variabel binding
            binding.executePendingBindings() // Mengupdate tampilan segera

            Glide.with(binding.root)
                .load(news.urlImage)
                .into(binding.imageView)
        }
    }
}
