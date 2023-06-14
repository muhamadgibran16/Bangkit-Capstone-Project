package com.example.donorgo.activity.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.donorgo.R

class ListNewsAdapter(private val listNews: ArrayList<News>) : RecyclerView.Adapter<ListNewsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list_vertikal, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]

        Glide.with(holder.itemView.context)
            .load(news.photo)
            .into(holder.imgPhoto)

        holder.tvTitle.text = news.title
        holder.tvDate.text = news.date

        /*val mContext = holder.itemView.context

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, DetailNewsActivity::class.java)
            moveDetail.putExtra(DetailNewsActivity.EXTRA_TITLE, news.title)
            moveDetail.putExtra(DetailNewsActivity.EXTRA_PHOTO, news.photo)
            moveDetail.putExtra(DetailNewsActivity.EXTRA_DATE, news.date)
            mContext.startActivity(moveDetail)
        }*/
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_news)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_news)
        val tvDate: TextView = itemView.findViewById(R.id.posted_at)
    }
}