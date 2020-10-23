package com.alexandr7035.fac

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = "DEBUG_FAC"

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show()
        Log.d(LOG_TAG, "alarm triggered")

    }
}