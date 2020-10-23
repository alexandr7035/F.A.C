package com.alexandr7035.fac

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*


class AlarmActivity : AppCompatActivity() {


    val LOG_TAG = "DEBUG_FAC"
    private lateinit var vibrator: Vibrator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Close activity on navigation btn click
        toolbar.setNavigationOnClickListener { finish() }

        // Set "add alarm" header if no alarm_id passed
        // (if new alarm is creating)
        if (intent.getIntExtra("ALARM_ID", 0) == 0) {
            toolbar.toolbarTitle.text = getString(R.string.add_alarm_title)
        }

        // FIX ME
        // TEST BLOCK

        val calendar: Calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 32)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        Log.d(LOG_TAG, "set alarm at " + calendar.timeInMillis)

        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        intent = Intent(this, AlarmReceiver::class.java)

        val pendingIntent : PendingIntent = PendingIntent.getBroadcast(
            this,
            1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent);

    }

}
