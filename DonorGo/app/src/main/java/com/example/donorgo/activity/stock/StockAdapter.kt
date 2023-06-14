package com.example.donorgo.activity.stock

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.databinding.ItemBloodStockBinding

class StockAdapter : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private val stockList: MutableList<ItemStock> = mutableListOf()

    fun setStockList(stock: List<ItemStock>) {
        stockList.clear()
        stockList.addAll(stock)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBloodStockBinding.inflate(inflater, parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.bind(stock)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    inner class StockViewHolder(private val binding: ItemBloodStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: ItemStock) {
            Log.d("StockAdapter", "Nama Rs: ${stock.namaRs}")
            Log.d("StockAdapter", "Alamat Rs: ${stock.alamatRs}")
            Log.d("StockAdapter", "Tipe Darah: ${stock.tipeDarah}")
            Log.d("StockAdapter", "Rhesus: ${stock.rhesus}")
            Log.d("StockAdapter", "Jumlah Kantong: ${stock.stock}")

            val stockText = stock.stock.toString() // Mengonversi stock.stock menjadi String
            binding.itemStock = stock // Mengatur objek stock sebagai variabel itemStock
            binding.jmlKantong.text = stockText // Mengatur jumlah kantong sebagai teks pada TextView
            binding.executePendingBindings() // Mengupdate tampilan segera
        }
    }

}
