package com.alexandr7035.fac.db

import android.content.Context
import androidx.lifecycle.LiveData

class AlarmsRepository(private val context: Context) {

    var db: AlarmsDB
    private var dao: AlarmsDao

    init {
        db = AlarmsDB.getInstance(context = this.context);
        dao = db.getDao()
    }

    fun getAlarms(): LiveData<List<AlarmEntity>> {
        return dao.getAlarms()
    }


}