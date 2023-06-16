package com.example.donorgo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RequestBloodDao {

    // MediatorLiveData Without
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpToDateData(request: List<TabelBloodRequest>)

    @Query("DELETE FROM Tabel_Blood_Request")
    fun deleteAllData()

    @Query("SELECT * FROM Tabel_Blood_Request ORDER BY createdAt ASC")
    fun getAllRequestFromDB(): LiveData<List<TabelBloodRequest>>

    @Query("SELECT COUNT(*) FROM Tabel_Blood_Request")
    fun getTabelRequestSize(): LiveData<Int>

    @Query("SELECT * FROM Tabel_Blood_Request WHERE nama_rs LIKE '%' || :keyword || '%'")
    fun searchByKeyword(keyword: String): LiveData<List<TabelBloodRequest>>

}