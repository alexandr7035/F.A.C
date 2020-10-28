package com.alexandr7035.fac.db

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import java.util.concurrent.*
import kotlinx.coroutines.launch

class AlarmsRepository(private val context: Context) {

    private var db: AlarmsDB
    private var dao: AlarmsDao
    private var executor: ExecutorService

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

    fun getAlarmFromDB(id: Int): LiveData<AlarmEntity> {
        return dao.getAlarmById(id)
    }


}