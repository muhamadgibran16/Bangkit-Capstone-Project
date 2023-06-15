package com.example.donorgo.activity.detail_request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R

class SyaratDetailAdapter(private var mList: List<SyaratDetail>) :
    RecyclerView.Adapter<SyaratDetailAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.titleFAQ)
        val faqDesc: TextView = itemView.findViewById(R.id.descFAQ)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayoutFAQ)

        fun collapseExpandedView(){
            faqDesc.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_faq, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        val syaratDetailData = mList[position]
        holder.titleTv.text = syaratDetailData.title
        holder.faqDesc.text = syaratDetailData.desc

        val isExpandable: Boolean = syaratDetailData.isExpandable
        holder.faqDesc.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            syaratDetailData.isExpandable = !syaratDetailData.isExpandable
            notifyItemChanged(position , Unit)
        }

    }

    private fun isAnyItemExpanded(position: Int){
        val temp = mList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            mList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onBindViewHolder(
        holder: LanguageViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}