package com.example.kost_ku_27.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Kost::class],
    version = 1
)
abstract class KostDb : RoomDatabase(){

    abstract fun kostDao() : KostDao

    companion object {

        @Volatile
        private var instance: KostDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: builDatabase(context).also {
                instance = it
            }
        }

        private fun builDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            KostDb::class.java,
            "kost12345.db"
        ).build()
    }
}