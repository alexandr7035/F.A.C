package com.alexandr7035.fac.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlarmEntity::class], version = 1)
abstract class AlarmsDB : RoomDatabase() {

    abstract fun getDao(): AlarmsDao

    companion object {

        private var sInstance: AlarmsDB? = null
        private const val dbName: String = "alarms.db"

        // Ge the singleton instance of SampleDatabase
        @Synchronized
        fun getInstance(context: Context): AlarmsDB {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(context.applicationContext, AlarmsDB::class.java, dbName)
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build()
            }
            return sInstance!!
        }
    }

}