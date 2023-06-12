package com.tasya.myapplication.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            binding.titleTextView.text = news.title
            binding.urlTextView.text = news.url
            // Bind other views as needed

            // You can also set click listeners or perform other actions on the views
            // For example:
            binding.root.setOnClickListener {
                // Handle item click event
            }
        }
    }
}