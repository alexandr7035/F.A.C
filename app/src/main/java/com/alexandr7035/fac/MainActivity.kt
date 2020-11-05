package com.alexandr7035.fac

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandr7035.fac.adapters.AlarmsListAdapter
import com.alexandr7035.fac.alarm.AlarmController
import com.alexandr7035.fac.alarm.AlarmNotification
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.db.AlarmsRepository
import com.alexandr7035.fac.viewmodel.MainViewModel
import com.alexandr7035.fac.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String = "DEBUG_FAC"
    private val adapter: AlarmsListAdapter = AlarmsListAdapter()
    private lateinit var viewModel: MainViewModel

    private lateinit var defaultClickListener: DefaultItemClickListener
    private lateinit var toggleClickListenerr: ToggleClickListener

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

        // For alarm toggle btn
        toggleClickListenerr = ToggleClickListener()
        adapter.setAlarmToggleBtnClickListener(toggleClickListenerr)

    }


    fun addAlarmBtn(v: View) {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }


    inner class DefaultItemClickListener: AlarmsListAdapter.AlarmClickListener {
        override fun onAlarmClick(alarm_id: Int, position: Int) {
            Log.d(LOG_TAG, "clicked position $position id $alarm_id");
            val intent = Intent(this@MainActivity, AlarmActivity::class.java)
            intent.putExtra("PASSED_ALARM_ID", alarm_id)
            startActivity(intent)
        }
    }

    inner class ToggleClickListener: AlarmsListAdapter.AlarmToggleBtnClickListener {
        override fun onAlarmToggleBtnClick(alarm_id: Int, position: Int) {
            Log.d(LOG_TAG, "toggle btn clicked")

            val ctx = this@MainActivity as Context

            GlobalScope.launch {

                val alarm = AlarmsRepository(ctx).getAlarmFromDB(1)

                if (alarm.enabled) {
                    AlarmController.disableAlarm(ctx, alarm)
                }
                else {
                    AlarmController.disableAlarm(ctx, alarm)
                    AlarmController.enableAlarm(ctx, alarm)
                }

            }

            adapter.notifyItemChanged(position)

        }

    }


    // FIXME
    fun enableNotifications(v: View) {


        val ctx = this as Context

        GlobalScope.launch {
            val alarm = AlarmsRepository(ctx).getAlarmFromDB(1)


            AlarmController.enableAlarm(ctx, alarm)
        }


    }


    // FIXME
    fun disableNotifications(v: View) {

        val ctx = this as Context

        GlobalScope.launch {
            val alarm = AlarmsRepository(ctx).getAlarmFromDB(1)

            AlarmController.disableAlarm(ctx, alarm)
        }


    }


}
