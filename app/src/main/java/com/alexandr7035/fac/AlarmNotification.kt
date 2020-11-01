package com.alexandr7035.fac

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alexandr7035.fac.db.AlarmEntity

class AlarmNotification(private var context: Context, alarm: AlarmEntity) {

    private val channelId = "FAC_CHANNEL"
    private val builder = NotificationCompat.Builder(context, channelId)

    private var notificationId: Int = 0

    init {
        builder.setContentTitle(alarm.name)
        builder.setContentText("Будильник - ${alarm.hours}:${alarm.minutes}")
        builder.setSmallIcon(R.drawable.ic_alarm_clock)
        builder.priority = NotificationCompat.PRIORITY_MAX

        notificationId = genId()

    }

    fun show() {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }


    // FIXME
    // Could be improved
    private fun genId(): Int {
        return  (0..32000).random()
    }

}