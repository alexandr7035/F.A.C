package com.alexandr7035.fac.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsRepository

class MainViewModel(context: Context) : ViewModel() {

    var repository: AlarmsRepository = AlarmsRepository(context)

    fun getAlarms(): LiveData<List<AlarmEntity>> {
        return repository.getAlarmsFromDB()
    }

}