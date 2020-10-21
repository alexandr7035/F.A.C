package com.alexandr7035.fac

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandr7035.fac.adapters.AlarmsListAdapter
import com.alexandr7035.fac.db.AlarmEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String = "DEBUG_FAC"
    private val adapter: AlarmsListAdapter = AlarmsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmsRecyclerView.adapter = adapter
        alarmsRecyclerView.layoutManager = LinearLayoutManager(this)

        var testEntities: ArrayList<AlarmEntity> = ArrayList()
        testEntities.add(AlarmEntity(name="firt"))
        testEntities.add(AlarmEntity(name="second"))
        testEntities.add(AlarmEntity(name="third"))

        Log.d(LOG_TAG, testEntities.toString())


        adapter.setItems(testEntities)
        adapter.notifyDataSetChanged()


    }

    fun addAlarmBtn(v: View) {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }

}
