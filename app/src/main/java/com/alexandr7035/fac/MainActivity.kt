package com.alexandr7035.fac

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandr7035.fac.adapters.AlarmsListAdapter
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.viewmodel.MainViewModel
import com.alexandr7035.fac.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String = "DEBUG_FAC"
    private val adapter: AlarmsListAdapter = AlarmsListAdapter()
    private lateinit var viewModel: MainViewModel

    private lateinit var defaultClickListener: DefaultItemClickListener

    var notId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmsRecyclerView.adapter = adapter
        alarmsRecyclerView.layoutManager = LinearLayoutManager(this)


        // ViewModel & LiveData
        viewModel = ViewModelProvider(this, MainViewModelFactory(this)).get<MainViewModel>(
                MainViewModel::class.java
            )

        val alarmsLiveData: LiveData<List<AlarmEntity>> = viewModel.getAlarms()


        // Update RecyclerView when dataset list changed
        alarmsLiveData.observe(this, Observer<List<AlarmEntity>> { alarms ->
            if (alarms != null ) {
                adapter.setItems(alarms)
            }
        })

        defaultClickListener = DefaultItemClickListener()
        adapter.setAlarmClickListener(defaultClickListener)

    }


    fun addAlarmBtn(v: View) {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }


    inner class DefaultItemClickListener: AlarmsListAdapter.AlarmClickListener {
        override fun onAlarmClick(alarm_id: Int, position: Int) {
            //Log.d(LOG_TAG, "clicked position " + position + " id " + skill_id);
            val intent = Intent(this@MainActivity, AlarmActivity::class.java)
            intent.putExtra("PASSED_ALARM_ID", alarm_id)
            startActivity(intent)
        }
    }


    // FIXME
    fun testNotifications(v: View) {

       notId += 1

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "FAC_CHANNEL", "F.A.C notifications channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = ""
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(false)
                notificationManager.createNotificationChannel(channel)
            }


            var builder = NotificationCompat.Builder(this, "FAC_CHANNEL")
                .setContentTitle("TITLE")
                .setContentText("Alarm")
                .setSmallIcon(R.drawable.ic_alarm_clock)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }
    }
}
