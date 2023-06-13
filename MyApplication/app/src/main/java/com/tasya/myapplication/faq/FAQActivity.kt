package com.tasya.myapplication.faq

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasya.myapplication.R
import java.util.*

class FAQActivity : AppCompatActivity() {
    private lateinit var recyclerViewFAQ: RecyclerView
    private var mListFAQ = ArrayList<FAQData>()
    private lateinit var adapterFAQ: FAQAdapter
    private lateinit var searchViewFAQ: androidx.appcompat.widget.SearchView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqactivity)

        recyclerViewFAQ = findViewById(R.id.recyclerViewFAQ)
        searchViewFAQ = findViewById(R.id.searchViewFAQ)

        recyclerViewFAQ.setHasFixedSize(true)
        recyclerViewFAQ.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapterFAQ = FAQAdapter(mListFAQ)
        recyclerViewFAQ.adapter = adapterFAQ

        searchViewFAQ.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = java.util.ArrayList<FAQData>()
            for (i in mListFAQ) {
                if (i.desc.lowercase(Locale.ROOT).contains(query) and i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapterFAQ.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mListFAQ.add(
            FAQData(
                getString(R.string.faq1),
                "Kebutuhan yang besar : Setiap delapan detik, ada satu orang yang membutuhkan transfusi darah di Indonesia.\n" +
                        "Pemeriksaan kesehatan gratis : Sebelum mendonorkan darah, petugas akan memeriksa suhu tubuh, denyut nadi, tekanan darah dan kadar hemoglobin Anda.\n" +
                        "Tidak menyakitkan : Ya Anda memang akan merasa sakit. Namun, rasa sakit itu tidak seberapa dan akan hilang dengan cepat."
            )
        )

        mListFAQ.add(
            FAQData(
                getString(R.string.faq2),
                "Kita memerlukan tidur yang nyenyak di malam sebelum mendonor, sarapan pagi atau makan siang sebelum mendonor. Banyak minum seperti jus, susu sebelum mendonor. Rileks saat mendonor, dan banyak minum setelah mendonor. Kita bisa melanjutkan kegiatan setelah mendonor, asal hindari aktivitas fisik yang berat."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq3),
                getString(R.string.syarat_detail)
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq4),
                "Mempunyai penyakit jantung dan paru; menderita kanker; menderita tekanan darah tinggi (hipertensi); menderita kencing manis (diabetes militus); memiliki kecenderungan perdarahan abnormal atau kelainan darah lainnya; menderita epilepsi dan sering kejang; menderita atau pernah menderita hepatitis B atau C; mengidap sifilis; ketergantungan narkoba; kecanduan minuman beralkohol; mengidap atau beresiko tinggi terhadap HIV/AIDS; dokter menyarankan untuk tidak menyubangkan darah karena alasan kesehatan."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq5),
                "Ya, Bila seseorang mendonorkan darahnya, tubuhnya akan menggantikan volume darah dalam waktu 48 jam setelah donor, dan semua sel darah merah yang hilang akan benar-benar diganti dalam waktu empat sampai delapan minggu dengan sel-sel darah merah yang baru. Proses pembentukan sel-sel darah merah yang baru akan membantu tubuh tetap sehat dan bekerja lebih efisien dan produktif."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq6),
                "Sedang sakit demam atau influenza tunggu 1 minggu setelah sembuh; setelah cabut gigi, tunggu 5 hari setalah sembuh; setelah operasi kecil, tunggu 6 bulan; setelah operasi besar, tunggu 1 tahun; setelah transfusi, tunggu 1 tahun; setelah tato, tindik, tusuk jarum, dan transplantasi tunggu 1 tahun; bila kontak erat dengan penderita hepatitis, tunggu 1 tahun; sedang hamil, tunggu 6 setelah melahirkan; sedang menyusui, tunggu 3 bulan setelah berhenti menyusui; setelah sakit malaria, tunggu 3 tahun setalah bebas dari gejala malaria; setelah berkunjung dari daerah endemis malaria, tunggu 1 tahun; bila tinggal di daerah endemis malaria selama 5 tahun berturut-turut, tunggu 3 tahun setelah keluar dari daerah tersebut; bila sakit tipus, tunggu 6 bulan setelah sembuh; setelah vaksin, tunggu 8 minggu; ada gejala alergi tunggu 1 minggu setalah sembuh; ada infeksi kulit pada daerah yang akan ditusuk, tunggu 1 minggu setelah sembuh."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq7),
                "Apheresis adalah metode baru yang aman dalam pengkoleksian komponen darah, mudah dan lebih efektif daripada cara konvensional. Dalam donasi darah pada metode apheresis, hanya komponen darah tertentu yang dikoleksi (seperti trombosit, plasma atau sel darah merah) sehingga komponen yang tidak diperlukan dikembalikan kedalam tubuh pendonor. Satu kantong donor trombosit apheresis setara dengan 6 - 10 kantong donor trombosit biasa. Sebuah transfusi apheresis dari donor tunggal sangat baik mengurangi resiko terjadinya reaksi sistem imun pada proses transfusi dan juga mengurangi resiko terinfeksinya bakteri karena hanya menerima darah dari donor tunggal sedangkan cara konvensional berasal dari banyak pendonor."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq8),
                "Trombosit adalah komponen dalam darah yang berperan penting untuk pembekuan darah. (Sering disebut platelet)."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq9),
                "Rhesus adalah suatu sistem penggolongan darah berupa POSITIF / NEGATIF setelah A/B/AB/O yang umumnya sudah kita ketahui."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq11),
                "Rhesus Negatif merupakan darah langka. Pemilih darah rhesus negatif hanya 15% di dunia dan Indonesia hanya memiliki 1% diantaranya "
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq10),
                "Pentingnya mengetahui rhesus adalah untuk kegiatan transfusi darah, dengan Jumlah rhesus negatif yang sedikit menjadikan kendala dalam menjaga ketersediaan stok darah. Pada permintaan darah rhesus negatif, baik pendonor maupun pasien sama-sama memiliki rhesus negatif."
            )
        )
        mListFAQ.add(
            FAQData(
                getString(R.string.faq12),
                "Jika darahmu Rhesus Negatif sedangkan kamu tidak mengetahui nya, jika suatu saat dalam keadaan darurat dan kamu butuh transfusi darah maka sulit akan mencari relawan denagn resus negatif"
            )
        )
        mListFAQ.add(
            FAQData(
                "Tentang Kemanusiaan",
                getString(R.string.tentang_kemanusiaan)
            )
        )
    }
}