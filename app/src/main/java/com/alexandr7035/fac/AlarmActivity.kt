package com.alexandr7035.fac

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_main.view.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        // Close activity on navigation btn click
        toolbar.setNavigationOnClickListener { finish() }

        // Set "add alarm" header if no alarm_id passed
        // (if new alarm is creating)
        if (intent.getIntExtra("ALARM_ID", 0) == 0) {
            toolbar.toolbarTitle.text = getString(R.string.add_alarm_title)
        }



    }
}
