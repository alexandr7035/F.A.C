package com.alexandr7035.fac.services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.util.Log


class SoundService: Service() {

    lateinit var player: MediaPlayer
    private val LOG_TAG = "DEBUG_FAC"

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        player = MediaPlayer()
        player.isLooping = true

        val defaultRingtoneUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        Log.d(LOG_TAG, defaultRingtoneUri.toString())

        try {

            player.setDataSource(this, defaultRingtoneUri)
            //player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
            )
            player.prepare()
            player.setOnPreparedListener {
                player.start()
            }

            player.setOnCompletionListener(OnCompletionListener { mp -> mp.release() })

        } catch (e: Exception) {
            Log.d(LOG_TAG, "exception during playing audio")
            Log.d(LOG_TAG, e.toString())
            e.printStackTrace()
        }


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        stopSelf()
        super.onDestroy()
    }


}