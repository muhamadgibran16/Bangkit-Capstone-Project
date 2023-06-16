package com.example.donorgo.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Tabel_Blood_Request")
@Parcelize
class TabelBloodRequest (
    @PrimaryKey
    @ColumnInfo("id_request")
    val idRequest: String,

    @ColumnInfo("alamat_rs")
    val alamatRs: String,

    @ColumnInfo("nama_pasien")
    val namaPasien: String,

    @ColumnInfo("kota")
    val kota: String,

    @ColumnInfo("gender")
    val gender: String,

    @ColumnInfo("latitude")
    val latitude: String,

    @ColumnInfo("telp_keluarga")
    val telpKeluarga: String,

    @ColumnInfo("createdAt")
    val createdAt: String,

    @ColumnInfo("rhesus")
    val rhesus: String,

    @ColumnInfo("nama_keluarga")
    val namaKeluarga: String,

    @ColumnInfo("nama_rs")
    val namaRs: String,

    @ColumnInfo("tipe_darah")
    val tipeDarah: String,

    @ColumnInfo("deskripsi")
    val deskripsi: String,

    @ColumnInfo("jml_kantong")
    val jmlKantong: Int,

    @ColumnInfo("prov")
    val prov: String,

    @ColumnInfo("longitude")
    val longitude: String

) : Parcelable
