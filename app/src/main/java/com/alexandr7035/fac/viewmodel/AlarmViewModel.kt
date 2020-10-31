package com.alexandr7035.fac.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsRepository

class AlarmViewModel(context: Context) : ViewModel() {

    var repository: AlarmsRepository = AlarmsRepository(context)

    fun addAlarm(alarm: AlarmEntity) {
        repository.addAlarmToDB(alarm)
    }

    fun getAlarmById(id: Int): MutableLiveData<AlarmEntity> {
        return repository.getAlarmFromDB(id)
    }

    fun updateAlarm(alarm: AlarmEntity) {
        repository.updateAlarmInDB(alarm)
    }

}