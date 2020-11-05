package com.alexandr7035.fac

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexandr7035.fac.alarm.AlarmController
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.viewmodel.AlarmViewModel
import com.alexandr7035.fac.viewmodel.AlarmViewModelFactory
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.Calendar


class AlarmActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {


    val LOG_TAG = "DEBUG_FAC"
    private lateinit var vibrator: Vibrator
    private lateinit var viewModel: AlarmViewModel
    private lateinit var alarmLiveData: MutableLiveData<AlarmEntity>
    private var passedAlarmId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        // ViewModel & LiveData
        viewModel = ViewModelProvider(this, AlarmViewModelFactory(this)).get<AlarmViewModel>(
           AlarmViewModel::class.java)

        passedAlarmId = intent.getIntExtra("PASSED_ALARM_ID", 0)

        // Load LiveData from DB or create new
        if (passedAlarmId != 0) {
            alarmLiveData = viewModel.getAlarmById(passedAlarmId)
        }
        // Set params for a new alarm
        else {

            val newAlarm = AlarmEntity()
            val initialCalendar = Calendar.getInstance()

            newAlarm.name = getString(R.string.alarm_default_name)
            newAlarm.hours = initialCalendar.get(Calendar.HOUR_OF_DAY)
            newAlarm.minutes = initialCalendar.get(Calendar.MINUTE)
            newAlarm.enabled = true

            alarmLiveData = MutableLiveData<AlarmEntity>(newAlarm)

        }


        alarmLiveData.observe(this, Observer<AlarmEntity> { alarm ->
            if (alarm != null ) {
                toolbarTitle.text = alarm.name

                // Add leading zeroes where necessary using String.format
                hoursView.text = String.format("%02d", alarm.hours)
                minutesView.text = String.format("%02d", alarm.minutes)
                // Need to use setter because text property requires Editable
                nameView.setText(alarm.name)
            }
        })


        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Toolbar settings
        toolbar.inflateMenu(R.menu.menu_alarm_activity)
        toolbar.setOnMenuItemClickListener(this)
        // Close activity on navigation btn click
        toolbar.setNavigationOnClickListener { finish() }

    }

    private fun saveAlarm() {


        val alarm = AlarmEntity()
        alarm.hours = hoursView.text.toString().toInt()
        alarm.minutes = minutesView.text.toString().toInt()
        alarm.name = nameView.text.toString()

        Log.d(LOG_TAG, alarm.toString())


        // Create new alarm or update if already exists in db
        if (passedAlarmId == 0) {
            viewModel.addAlarm(alarm)
        }
        else {
            alarm.id = passedAlarmId
            viewModel.updateAlarm(alarm)
        }


        if (alarm.enabled) {
            AlarmController.disableAlarm(this, alarm)
            AlarmController.enableAlarm(this, alarm)
        }
        else {
            AlarmController.disableAlarm(this, alarm)
        }

        finish()

    }



    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_save_alarm) {
            Toast.makeText(this, "Save alarm", Toast.LENGTH_LONG).show()

            saveAlarm()
        }

        return super.onOptionsItemSelected(item);
    }



    fun increaseHourBtn(v: View) {

        val alarmEntity = alarmLiveData.value

        if (alarmEntity != null) {
            if (alarmEntity.hours < 23) {
                alarmEntity.hours += 1
            }
            else {
                alarmEntity.hours = 0
            }
        }

        Log.d(LOG_TAG, "increase hours clicked")

        alarmLiveData.value = alarmEntity

    }

    fun decreaseHoursBtn(v: View) {
        val alarmEntity = alarmLiveData.value

        if (alarmEntity != null) {
            if (alarmEntity.hours > 0) {
                alarmEntity.hours -= 1
            }
            else {
                alarmEntity.hours = 23
            }
        }

        Log.d(LOG_TAG, "decrease hours clicked")

        alarmLiveData.value = alarmEntity

    }

    fun increaseMinutesBtn(v: View) {

        val alarmEntity = alarmLiveData.value

        if (alarmEntity != null) {
            if (alarmEntity.minutes < 59) {
                alarmEntity.minutes += 1
            }
            else {
                alarmEntity.minutes = 0
            }
        }

        Log.d(LOG_TAG, "increase minutes clicked")

        alarmLiveData.value = alarmEntity


    }

    fun decreaseMinutesBtn(v: View) {
        val alarmEntity = alarmLiveData.value

        if (alarmEntity != null) {
            if (alarmEntity.minutes > 0) {
                alarmEntity.minutes -= 1
            }
            else {
                alarmEntity.minutes = 59
            }
        }

        Log.d(LOG_TAG, "decrease minutes clicked")

        alarmLiveData.value = alarmEntity

    }



}
