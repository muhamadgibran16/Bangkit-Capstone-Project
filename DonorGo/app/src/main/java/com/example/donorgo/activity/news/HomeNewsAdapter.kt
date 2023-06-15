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
import com.example.donorgo.activity.web_view.WebViewNewsActivity

class HomeNewsAdapter(private val listNews: ArrayList<News>)
    : RecyclerView.Adapter<HomeNewsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list_home, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]

        Glide.with(holder.itemView.context)
            .load(news.photo)
            .into(holder.imgPhoto)

        holder.tvTitle.text = news.title
        holder.tvDate.text = news.date

        val mContext = holder.itemView.context

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, WebViewNewsActivity::class.java)
            moveDetail.putExtra(WebViewNewsActivity.NEWS_EXTRA_DATA, news)
            moveDetail.putExtra(WebViewNewsActivity.SOURCE_PAGE, HOME_PAGE)
            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_news)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_news)
        val tvDate: TextView = itemView.findViewById(R.id.posted_at)
    }

    companion object {
        const val HOME_PAGE = "home_page"
    }
}