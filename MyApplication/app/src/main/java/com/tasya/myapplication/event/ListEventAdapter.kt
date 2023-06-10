package com.tasya.myapplication.event

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tasya.myapplication.R

class ListEventAdapter (private val listEvent : ArrayList<Event>) : RecyclerView.Adapter<ListEventAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_event, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = listEvent[position]

        Glide.with(holder.itemView.context)
            .load(event.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)

        holder.tvName.text = event.name
        holder.tvDescription.text = event.title

        val mContext = holder.itemView.context

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, DetailEventActivity::class.java)
            moveDetail.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
            moveDetail.putExtra(DetailEventActivity.EXTRA_PHOTO, event.photo)
            moveDetail.putExtra(DetailEventActivity.EXTRA_DESCRIPTION, event.title)
            moveDetail.putExtra(DetailEventActivity.EXTRA_RILIS, event.rilis)
            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }
}