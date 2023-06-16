package com.example.donorgo.activity.news

import com.example.donorgo.R

object NewsData {
    private val newsTitle = arrayOf(
        "7 Syarat Umum yang Harus Dipenuhi Sebelum Donor Darah",
        "Riau Butuh 7 Ribu Kantong Darah Setiap Bulan, PMI Dorong Warga Berdonor",
        "PMI Pekanbaru apresiasi Donor Darah IKBI PTPN V",
        "Cen Sui Lan Gandeng Sejumlah Organisasi Gelar Donor Darah di Tanjungpinang",
        "Apresiasi Donor Darah Kopdar Kepri, Kadinkes Tanjungpinang : Ini Pekerjaan Mulia",
        "Perwakilan BPKP Kepulauan Riau Hadir Bermanfaat Melalui Kegiatan Donor Darah",
        "Donor Darah Untuk Kemanusiaan, Grand Zuri Duri Kolaborasi dengan Komunitas"
    )

    private val newsUrl = arrayOf(
        "https://www.halodoc.com/artikel/7-syarat-umum-yang-harus-dipenuhi-sebelum-donor-darah ",
        "https://www.cakaplah.com/berita/baca/91144/2022/10/24/riau-butuh-7-ribu-kantong-darah-setiap-bulan-pmi-dorong-warga-berdonor",
        "https://riau.antaranews.com/berita/302469/pmi-pekanbaru-apresiasi-donor-darah-ikbi-ptpn-v ",
        "https://www.batamnews.co.id/berita-97465-cen-sui-lan-gandeng-sejumlah-organisasi-gelar-donor-darah-di-tanjungpinang.html",
        "https://www.tanjungpinangkota.go.id/berita/apresiasi-donor-darah-kopdar-kepri-kadinkes-tanjungpinang-ini-pekerjaan-mulia",
        "https://www.bpkp.go.id/berita/readunit/44/42391/0/Perwakilan-BPKP-Kepulauan-Riau-Hadir-Bermanfaat-Melalui-Kegiatan-Donor-Darah ",
        "https://www.goriau.com/berita/baca/donor-darah-untuk-kemanusiaan-grand-zuri-duri-kolaborasi-dengan-komunitas.html",
    )

    private val newsImage = intArrayOf(
        R.drawable.news1,
        R.drawable.news2,
        R.drawable.news3,
        R.drawable.news5,
        R.drawable.news7,
        R.drawable.news8,
        R.drawable.news4
    )

    private val newsDate = arrayOf(
        "05 Juni 2023",
        "24 Oktober 2022 ",
        "16 September 2022 ",
        "21 Maret 2023",
        "21 Maret 2022",
        "22 Mei 2023 ",
        "06 Mei 2017"
    )

    val listData: ArrayList<News>
        get() {
            val list = arrayListOf<News>()
            for (position in newsTitle.indices) {
                val news = News()
                news.title = newsTitle[position]
                news.url = newsUrl[position]
                news.photo = newsImage[position]
                news.date = newsDate[position]
                list.add(news)
            }
            return list
        }
}