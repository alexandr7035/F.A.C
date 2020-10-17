package com.alexandr7035.fac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsDB
import com.alexandr7035.fac.db.AlarmsDao
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG: String = "DEBUG_FAC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: AlarmsDB = AlarmsDB.getInstance(this)
        val dao: AlarmsDao = db.getDao()

        var alarmEntity = AlarmEntity(name = "Будильник")
        alarmEntity.time = 142345345345

        dao.insert(alarmEntity)

        Log.d(LOG_TAG, "entity $alarmEntity")


    }

}
