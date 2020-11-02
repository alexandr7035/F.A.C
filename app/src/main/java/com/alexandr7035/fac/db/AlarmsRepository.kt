package com.alexandr7035.fac.db

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import java.util.concurrent.*
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData

class AlarmsRepository(private val context: Context) {

    private var db: AlarmsDB
    private var dao: AlarmsDao
    private var executor: ExecutorService
    val LOG_TAG = "DEBUG_FAC"

    lateinit var alarmLiveData: MutableLiveData<AlarmEntity>

    init {
        db = AlarmsDB.getInstance(context = this.context);
        dao = db.getDao()
        executor = Executors.newSingleThreadExecutor()
    }


    fun getAlarmsFromDB(): LiveData<List<AlarmEntity>> {
        return dao.getAlarms()
    }


    fun addAlarmToDB(alarm: AlarmEntity) {
        GlobalScope.launch {
            dao.insert(alarm)
        }
    }


    fun getAlarmLiveDataFromDB(id: Int): MutableLiveData<AlarmEntity> {

        alarmLiveData = MutableLiveData()

        GlobalScope.launch {
            alarmLiveData.postValue(dao.getAlarmLiveDataById(id))
        }

        return alarmLiveData
    }


    fun updateAlarmInDB(alarm: AlarmEntity) {
        GlobalScope.launch {
            dao.update(alarm)
        }
    }


    fun getAlarmFromDB(id: Int): AlarmEntity {
        return dao.getAlarmaById(id)
    }

}