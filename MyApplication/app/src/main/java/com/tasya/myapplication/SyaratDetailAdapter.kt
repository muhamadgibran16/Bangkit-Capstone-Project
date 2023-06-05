package com.tasya.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class SyaratDetailAdapter(private var mList: List<SyaratDetailData>) :
    RecyclerView.Adapter<SyaratDetailAdapter.SyaratDetailViewHolder>() {

    inner class SyaratDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val DescTv: TextView = itemView.findViewById(R.id.langDesc)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun collapseExpandedView(){
            DescTv.visibility = View.GONE
        }
    }

    /*   fun setFilteredList(mList: List<SyaratDetailData>) {
           this.mList = mList
           notifyDataSetChanged()
       }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyaratDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_req, parent, false)
        return SyaratDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: SyaratDetailViewHolder, position: Int) {

        val syaratDetailData = mList[position]
        holder.logo.setImageResource(syaratDetailData.logo)
        holder.titleTv.text = syaratDetailData.title
        holder.DescTv.text = syaratDetailData.desc

        val isExpandable: Boolean = syaratDetailData.isExpandable
        holder.DescTv.visibility = if (isExpandable) View.VISIBLE else View.GONE

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
        holder: SyaratDetailViewHolder,
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