package com.example.donorgo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TabelBloodRequest::class],
    version = 1,
    exportSchema = true
)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun requestBloodDao(): RequestBloodDao

    companion object {
        @Volatile
        private var INSTANCE: LocalRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): LocalRoomDatabase {
            if (INSTANCE == null) {
                synchronized(LocalRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalRoomDatabase::class.java,
                        "request_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as LocalRoomDatabase
        }
    }
}