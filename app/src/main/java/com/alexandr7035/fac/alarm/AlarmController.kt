package com.alexandr7035.fac.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsRepository
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

            // Gen request code for pending intent
            // Save to db
            val requestCode = genRequestCode()
            alarm.pi_request_code = requestCode
            AlarmsRepository(context).updateAlarmInDB(alarm)

            val pendingIntent : PendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)


            val calendar: Calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, alarm.hours)
            calendar.set(Calendar.MINUTE, alarm.minutes)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)


            // If alarm set to past add 1 day
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }

            alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.timeInMillis,
                            pendingIntent
            );

        }


        fun disableAlarm(context: Context, alarm: AlarmEntity) {
            Log.d(LOG_TAG, "disable alarm called")
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)
            val requestCode = alarm.pi_request_code

            val pendingIntent : PendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            alarmManager.cancel(pendingIntent)


            // Reset request code in db
            alarm.pi_request_code = 0
            AlarmsRepository(context).updateAlarmInDB(alarm)

        }


        fun genRequestCode(): Int {
            return  (0..2147483647).random()
        }
    }

}