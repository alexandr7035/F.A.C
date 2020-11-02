package com.alexandr7035.fac.alarm

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alexandr7035.fac.R
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.services.SoundService

class AlarmNotification(private var context: Context, alarm: AlarmEntity) {

    private val channelId = "FAC_CHANNEL"
    private val builder = NotificationCompat.Builder(context, channelId)

    private var notificationId: Int = 0

    init {
        // Set params
        builder.setContentTitle(alarm.name)
        builder.setContentText("Будильник - ${alarm.hours}:${alarm.minutes}")
        builder.setSmallIcon(R.drawable.ic_alarm_clock)
        builder.priority = NotificationCompat.PRIORITY_MAX

        // Put buttons on notification
        builder.addAction(R.drawable.ic_alarm_clock_off, context.getString(R.string.fac_notification_action_stop),  null)
        builder.addAction(R.drawable.ic_alarm_clock_off, context.getString(R.string.fac_notification_action_hold), null)

        notificationId = genId()

    }

    fun show() {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())

            playSound()
        }
    }


    // FIXME
    // Could be improved
    private fun genId(): Int {
        return  (0..32000).random()
    }


    private fun playSound() {
        context.startService(Intent(context, SoundService::class.java))
    }

}