package com.alexandr7035.fac.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmsDao {

    @Insert
    fun insert(alarm: AlarmEntity)

    @Update
    fun update(alarm: AlarmEntity)

    @Delete
    fun delete(note: AlarmEntity)

    @Query("select * from alarms order by id")
    fun getAlarms(): LiveData<List<AlarmEntity>>
}