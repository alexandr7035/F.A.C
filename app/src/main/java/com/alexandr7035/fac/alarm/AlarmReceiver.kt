package com.alexandr7035.fac.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = "DEBUG_FAC"

    override fun onReceive(context: Context, intent: Intent) {



        val alarmId = intent.getIntExtra("ALARM_ID", 0)

        Log.d(LOG_TAG, "alarm triggered for alarm id $alarmId")

        GlobalScope.launch {
            val alarm: AlarmEntity = AlarmsRepository(context).getAlarmFromDB(alarmId)

            Log.d(LOG_TAG, "ALARM PASSED TO RECEIVER $alarm")

            val notification = AlarmNotification(context, alarm)
            notification.show()
        }

        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show()


    }
}