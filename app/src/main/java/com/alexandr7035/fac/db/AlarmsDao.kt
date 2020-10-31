package com.alexandr7035.fac.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmsDao {

    @Insert
    suspend fun insert(alarm: AlarmEntity)

    @Update
    suspend fun update(alarm: AlarmEntity)

    @Delete
    fun delete(note: AlarmEntity)

    @Query("select * from alarms order by id")
    fun getAlarms(): LiveData<List<AlarmEntity>>

    @Query("SELECT * FROM alarms WHERE id = (:id)")
    fun getAlarmById(id: Int): AlarmEntity
}