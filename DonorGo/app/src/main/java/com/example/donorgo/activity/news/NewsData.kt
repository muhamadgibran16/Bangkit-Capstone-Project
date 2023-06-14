package com.example.donorgo.activity.news

import com.example.donorgo.R

object NewsData {
    private val newsTitle = arrayOf(
        "7 Syarat Umum yang Harus Dipenuhi Sebelum Donor Darah",
        "Riau Butuh 7 Ribu Kantong Darah Setiap Bulan, PMI Dorong Warga Berdonor",
        "PMI Pekanbaru apresiasi Donor Darah IKBI PTPN V",
        "Cen Sui Lan Gandeng Sejumlah Organisasi Gelar Donor Darah di Tanjungpinang",
        "Apresiasi Donor Darah Kopdar Kepri, Kadinkes Tanjungpinang : Ini Pekerjaan Mulia",
        "Hotel Dafam Pekanbaru Gelar Donor Darah Bersama Komunitas Mobil",
        "Perwakilan BPKP Kepulauan Riau Hadir Bermanfaat Melalui Kegiatan Donor Darah",
        "Donor Darah Untuk Kemanusiaan, Grand Zuri Duri Kolaborasi dengan Komunitas"
    )

    private val newsImage = intArrayOf(
        R.drawable.news1,
        R.drawable.news2,
        R.drawable.news3,
        R.drawable.news5,
        R.drawable.news6,
        R.drawable.news7,
        R.drawable.news8,
        R.drawable.news4
        )

//    private val newsDetail = arrayOf(
//        "Donor darah ini terbuka untuk umum dan akan dilaksanakan pada hari Selasa - Rabu tanggal 2 - 3 Mei 2023 di Lapangan Upacara RSUD Arifin Achmad Provinsi Riau pukul 09:00 WIB. \n\nBagi Cik Puan yang ingin berpartisipasi dapat langsung mendaftar pada kontak person yang ada pada gambar atau langsung datang ke lokasi pada hari H.\n\nSave a life, Setetes Darah Anda adalah Harapan Hidup Bagi Saudara Kita yang Membutuhkan",
//
//        "'Kami berharap kegiatan yang akan kami adakan mulai tanggal 12 sampai 17 Maret 2023 pukul 08.00 sampai 16.00 WIB di Lancang Kuning Ballroom Hotel Furaya Pekanbaru. Ini akan bisa mengumpulkan kantong-kantong darah yang banyak sehingga bisa membantu PMI Kota Pekanbaru dalam melayani kebutuhan darah bagi masyarakat' kata Sarwie Tan.\n\nKepada masyarakat yang akan mendonor panitia juga menyiapkan pendaftaran secara online dengan mengisi form digital https://forms.gle/aeaFtyjUCjRdMuiX6 atau melalui sekretariat Relawan Peduli Covid-19 Riau dengan nomor telepon 0823 8888 6449.",
//
//        "Bagi Cik Puan yang bersedia untuk mendonorkan darahnya dapat langsung mendaftar melalui Contact Personal atau datang langsung pada hari H sesuai waktu dan tempat yang telah disebutkan diatas.\nAyo donor darah,\nSave a life 'Setetes Darah Anda Bukti Cinta Kepada Sesama'\n\nSalam MedikAA.",
//
//        "RSUD Arifin Achmad Provinsi Riau bersama Palang Merah Indonesia Kota Pekanbaru akan kembali mengadakan agenda rutin donor darah yang berlokasi di lapangan upacara RSUD Arifin Achmad Provinsi Riau pada hari selasa dan rabu (17 – 18 Mei 2022) pukul 09.00 WIB.\n\nBagi Cik Puan yang ingin mendonorkan darahnya dapat langsung datang ke lokasi pada Hari H atau mendaftar pada nomor Contact Person (CP) yang telah tertera di gambar."
//    )

//    private val newsStatus = arrayOf(
//        "On Going",
//
//        "On Going",
//
//        "Closed",
//
//        "Closed"
//    )

//    private val newsLocation= arrayOf(
//        "Lapangan Upacara RSUD Arifin Achmad",
//
//        "Lancang Kuning Ballroom Hotel Furaya Pekanbaru",
//
//        "Lapangan upacara RSUD Arifin Achmad",
//
//        "Lapangan upacara RSUD Arifin Achmad"
//    )

    private val newsDate = arrayOf(
        "05 Juni 2023",
        "24 Oktober 2022 ",
        "16 September 2022 ",
        "21 Maret 2023",
        "21 Maret 2022",
        "5 Maret 2023 ",
        "22 Mei 2023 ",
        "06 Mei 2017"
    )

    val listData: ArrayList<News>
        get() {
            val list = arrayListOf<News>()
            for (position in newsTitle.indices) {
                val news = News()
                //news.detail = newsDetail[position]
                news.title = newsTitle[position]
                //news.status = newsStatus[position]
                // news.location = newsLocation[position]
                news.photo = newsImage[position]
                news.date = newsDate[position]
                list.add(news)
            }
            return list
        }
}