package com.jax.cryptoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jax.cryptoapp.data.model.CoinPriceInfoDto

@Database(entities = [CoinPriceInfoDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var database: AppDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "main.db"
        fun getInstance(applicationContext: Context): AppDatabase {
            database?.let { return it }
            synchronized(LOCK) {
                database?.let { return it }
                val instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                database = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}