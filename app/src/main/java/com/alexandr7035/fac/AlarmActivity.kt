package com.alexandr7035.fac

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.alexandr7035.fac.db.AlarmEntity
import com.alexandr7035.fac.viewmodel.AlarmViewModel
import com.alexandr7035.fac.viewmodel.AlarmViewModelFactory
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*


class AlarmActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {


    val LOG_TAG = "DEBUG_FAC"
    private lateinit var vibrator: Vibrator
    private lateinit var viewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        // ViewModel
        viewModel = ViewModelProvider(this, AlarmViewModelFactory(this)).get<AlarmViewModel>(
           AlarmViewModel::class.java)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Toolbar settings
        toolbar.inflateMenu(R.menu.menu_alarm_activity)
        toolbar.setOnMenuItemClickListener(this)
        // Close activity on navigation btn click
        toolbar.setNavigationOnClickListener { finish() }


        // if no alarm_id passed
        // (if new alarm is creating)
        if (intent.getIntExtra("ALARM_ID", 0) == 0) {

            // Set "add alarm" header
            toolbar.toolbarTitle.text = getString(R.string.add_alarm_title)

            // Init clock with current time
            val initialCalendar = Calendar.getInstance()
            hoursView.text = initialCalendar.get(Calendar.HOUR_OF_DAY).toString()
            minutesView.text = initialCalendar.get(Calendar.MINUTE).toString()

        }
        else {

        }



    }

    private fun saveAlarm() {

        val alarm = AlarmEntity()
        alarm.hours = hoursView.text.toString().toInt()
        alarm.minutes = minutesView.text.toString().toInt()
        alarm.name = nameView.text.toString()
        alarm.enabled = true

        viewModel.addAlarm(alarm)

        finish()

        /*
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

      */
    }



    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_save_alarm) {
            Toast.makeText(this, "Save alarm", Toast.LENGTH_LONG).show()

            saveAlarm()
        }

        return super.onOptionsItemSelected(item);
    }

}
