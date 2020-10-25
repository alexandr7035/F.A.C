package com.alexandr7035.fac

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    }


    fun addAlarmBtn(v: View) {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }

}
