import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.databinding.ItemHistoryRequestVertikalBinding
import com.example.donorgo.activity.history.ItemHistory

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val historyList: MutableList<ItemHistory> = mutableListOf()

    fun setHistoryList(history: List<ItemHistory>) {
        historyList.clear()
        historyList.addAll(history)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryRequestVertikalBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryRequestVertikalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ItemHistory) {
            Log.d("HistoryAdapter", "Title: ${history.tipeDarah}")
            Log.d("HistoryAdapter", "Rhesus: ${history.rhesus}")
            Log.d("HistoryAdapter", "Kota: ${history.kota}")
            Log.d("HistoryAdapter", "Nama Pasien: ${history.namaPasien}")
            Log.d("HistoryAdapter", "Nama RS: ${history.namaRs}")
            binding.itemHistory = history // Mengatur objek history sebagai variabel itemHistory
            binding.executePendingBindings() // Mengupdate tampilan segera
        }
    }
}