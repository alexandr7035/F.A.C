package com.alexandr7035.fac.alarm

import android.R.id
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.alexandr7035.fac.services.SoundService


class NotificationActionReceiver :  BroadcastReceiver()  {

    private val LOG_TAG = "DEBUG_FAC"

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(LOG_TAG, "NOTIFICATION action called")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent.getIntExtra("NOTIFICATION_ID", 0)

        // Cancel notification
        notificationManager.cancel(notificationId)

        // Stop sound
        context.stopService(Intent(context, SoundService::class.java))

    }
}