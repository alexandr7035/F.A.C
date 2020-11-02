package com.alexandr7035.fac.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.alexandr7035.fac.db.AlarmEntity
import java.util.*

class AlarmController {



    // Make methods static
    companion object {

        private val LOG_TAG = "DEBUG_FAC"

        fun enableAlarm(context: Context, alarm: AlarmEntity) {

            Log.d(LOG_TAG, "enable allarm called")

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)

            // Pass task id to alarm receiver
            intent.putExtra("ALARM_ID", alarm.id)

            val pendingIntent : PendingIntent = PendingIntent.getBroadcast(
                context,
                1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)


            val calendar: Calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, alarm.hours)
            calendar.set(Calendar.MINUTE, alarm.minutes)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            alarmManager.setExact(
                            AlarmManager.RTC,
                            calendar.timeInMillis,
                            pendingIntent
            );

        }


        fun disableAlarm(context: Context) {

        }

    }

}