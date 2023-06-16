package com.example.donorgo.activity.stock

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.databinding.ItemBloodStockBinding
import com.example.donorgo.dataclass.StockItem

class StockAdapter : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private val stockList: MutableList<StockItem> = mutableListOf()
    private lateinit var binding: ItemBloodStockBinding

    fun setStockList(stock: List<StockItem>) {
        stockList.clear()
        stockList.addAll(stock)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockAdapter.StockViewHolder {
        binding = ItemBloodStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockAdapter.StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.bind(stock)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    inner class StockViewHolder(private val binding: ItemBloodStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: StockItem) {
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
