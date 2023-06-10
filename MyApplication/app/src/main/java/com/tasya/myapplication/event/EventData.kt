package com.tasya.myapplication.event

import com.tasya.myapplication.R

object EventData {
    private val eventTitle = arrayOf(
        "RSUD Arifin Achmad Provinsi Riau kembali akan mengadakan kegiatan donor darah rutin bersama Palang Merah Indonesia (PMI) Kota Pekanbaru.",
        "Relawan Peduli dan PMI Pekanbaru Kembali Gelar Donor Darah Massal Selama 6 Hari",
        "RSUD Arifin Achmad Provinsi Riau bersama PMI Kota Pekanbaru kembali akan mengadakan acara donor darah rutin.",
        "Ayo Ikuti Donor Darah Rutin Bersama RSUD Arifin Achmad Provinsi Riau Dan PMI Kota Pekanbaru"
    )

    private val eventImage = intArrayOf(
        R.drawable.event1,
        R.drawable.event2,
        R.drawable.event4,
        R.drawable.event3
    )

    private val eventDetail = arrayOf(
        "Donor darah ini terbuka untuk umum dan akan dilaksanakan pada hari Selasa - Rabu tanggal 2 - 3 Mei 2023 di Lapangan Upacara RSUD Arifin Achmad Provinsi Riau pukul 09:00 WIB. \n\nBagi Cik Puan yang ingin berpartisipasi dapat langsung mendaftar pada kontak person yang ada pada gambar atau langsung datang ke lokasi pada hari H.\n\nSave a life, Setetes Darah Anda adalah Harapan Hidup Bagi Saudara Kita yang Membutuhkan",

        "'Kami berharap kegiatan yang akan kami adakan mulai tanggal 12 sampai 17 Maret 2023 pukul 08.00 sampai 16.00 WIB di Lancang Kuning Ballroom Hotel Furaya Pekanbaru. Ini akan bisa mengumpulkan kantong-kantong darah yang banyak sehingga bisa membantu PMI Kota Pekanbaru dalam melayani kebutuhan darah bagi masyarakat' kata Sarwie Tan.\n\nKepada masyarakat yang akan mendonor panitia juga menyiapkan pendaftaran secara online dengan mengisi form digital https://forms.gle/aeaFtyjUCjRdMuiX6 atau melalui sekretariat Relawan Peduli Covid-19 Riau dengan nomor telepon 0823 8888 6449.",

        "Bagi Cik Puan yang bersedia untuk mendonorkan darahnya dapat langsung mendaftar melalui Contact Personal atau datang langsung pada hari H sesuai waktu dan tempat yang telah disebutkan diatas.\nAyo donor darah,\nSave a life 'Setetes Darah Anda Bukti Cinta Kepada Sesama'\n\nSalam MedikAA.",

        "RSUD Arifin Achmad Provinsi Riau bersama Palang Merah Indonesia Kota Pekanbaru akan kembali mengadakan agenda rutin donor darah yang berlokasi di lapangan upacara RSUD Arifin Achmad Provinsi Riau pada hari selasa dan rabu (17 – 18 Mei 2022) pukul 09.00 WIB.\n\nBagi Cik Puan yang ingin mendonorkan darahnya dapat langsung datang ke lokasi pada Hari H atau mendaftar pada nomor Contact Person (CP) yang telah tertera di gambar."
    )

    private val publishEvent = arrayOf(
        "Rabu, 26 Apr 2023",
        "Sabtu, 11 Maret 2023 ",
        "Minggu, 15 Mei 2022",
        "Jum'at, 10 Des 2021"
    )

    val listData: ArrayList<Event>
        get() {
            val list = arrayListOf<Event>()
            for (position in eventTitle.indices) {
                val event = Event()
                event.name = eventTitle[position]
                event.title = eventDetail[position]
                event.photo = eventImage[position]
                event.rilis = publishEvent[position]
                list.add(event)
            }
            return list
        }
}