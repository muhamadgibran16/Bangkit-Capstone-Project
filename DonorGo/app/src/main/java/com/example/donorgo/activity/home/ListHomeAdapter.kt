package com.example.donorgo.activity.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.databinding.ItemRequestListHomeBinding
import com.example.donorgo.dataclass.BloodRequestItem

class ListHomeAdapter(
    private val listBloodRequest: List<BloodRequestItem>,
    private val onClick: (BloodRequestItem) -> Unit,
) :
    ListAdapter<BloodRequestItem, ListHomeAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private lateinit var binding: ItemRequestListHomeBinding

    inner class ListViewHolder(var binding: ItemRequestListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemClick(request: BloodRequestItem) {
            binding.cvWrapperBloodRequest.setOnClickListener() {
                onClick(request)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHomeAdapter.ListViewHolder {
        binding =
            ItemRequestListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHomeAdapter.ListViewHolder, position: Int) {
        val request = listBloodRequest[position]
        Glide.with(holder.itemView)
            .load(R.drawable.ic_marker)
            .placeholder(R.drawable.ic_marker)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.imgHospital)

        holder.binding.apply {
            reshusDar.text = request.rhesus
            tvBloodType.text = request.tipeDarah
            tvCity.text = request.kota
            tvPasienName.text = request.namaPasien
            tvLocation.text = request.namaRs
        }
        holder.itemClick(request)
    }

    override fun getItemCount(): Int = listBloodRequest.size


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<BloodRequestItem> =
            object : DiffUtil.ItemCallback<BloodRequestItem>() {
                override fun areItemsTheSame(
                    oldItem: BloodRequestItem,
                    newItem: BloodRequestItem
                ): Boolean {
                    return oldItem.idRequest == newItem.idRequest
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: BloodRequestItem,
                    newItem: BloodRequestItem
                ): Boolean {
                    return oldItem.deskripsi == newItem.deskripsi && oldItem.createdAt == newItem.createdAt
                }
            }
    }
}