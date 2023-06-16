package com.example.donorgo.activity.event

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.donorgo.R


class ListEventAdapter(private val listEvent: ArrayList<Event>) :
    RecyclerView.Adapter<ListEventAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event_list_vertikal, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = listEvent[position]

        Glide.with(holder.itemView.context)
            .load(event.photo)
            .into(holder.imgPhoto)

        holder.tvTitle.text = event.detail
        holder.tvLocation.text = event.location
        holder.tvStatus.text = event.status
        holder.tvDate.text = event.date


        val mContext = holder.itemView.context

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, DetailEventActivity::class.java)
            moveDetail.putExtra(DetailEventActivity.EXTRA_TITLE, event.title)
            moveDetail.putExtra(DetailEventActivity.EXTRA_DETAIL, event.detail)
            moveDetail.putExtra(DetailEventActivity.EXTRA_PHOTO, event.photo)
            moveDetail.putExtra(DetailEventActivity.EXTRA_DATE, event.date)
            moveDetail.putExtra(DetailEventActivity.EXTRA_STATUS, event.status)
            moveDetail.putExtra(DetailEventActivity.EXTRA_LOCATION, event.location)
            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_content)
        val tvTitle: TextView = itemView.findViewById(R.id.title_event)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvStatus: TextView = itemView.findViewById(R.id.status_value)
    }
}