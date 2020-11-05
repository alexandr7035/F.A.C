package com.alexandr7035.fac.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandr7035.fac.R
import com.alexandr7035.fac.db.AlarmEntity
import kotlinx.android.synthetic.main.view_alarm.view.*

class AlarmsListAdapter : RecyclerView.Adapter<AlarmsListAdapter.ViewHolder>() {

    private var items: List<AlarmEntity> = ArrayList()
    val LOG_TAG = "DEBUG_FAC"
    private lateinit var alarmClickListener: AlarmClickListener
    private lateinit var alarmToggleBtnClickListener: AlarmToggleBtnClickListener

    fun setItems(items: List<AlarmEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_alarm, parent, false)
        return ViewHolder(itemView = view)

    }

    override fun onBindViewHolder(holder: AlarmsListAdapter.ViewHolder, position: Int) {

        // Set alarm name
        holder.itemView.nameView.text = items[position].name

        // Set time
        // Add leading zeroes where necessary using String.format
        val time = String.format("%02d", items[position].hours) + ":" + String.format("%02d", items[position].minutes)
        holder.itemView.timeView.text = time

        // Set icon to clock btn
        if (items[position].enabled){
            holder.itemView.alarmToggleBtn.setImageResource(R.drawable.ic_alarm_clock_off)
        }
        else {
            holder.itemView.alarmToggleBtn.setImageResource(R.drawable.ic_alarm_clock)
        }

        // Set id
        holder.alarmId = items.get(position).id


        Log.d(LOG_TAG, holder.itemView.isEnabled.toString())

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                                View.OnClickListener,
                                View.OnLongClickListener {

        // Set in onBindViewHolder
        var alarmId: Int = 0

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            itemView.alarmToggleBtn.setOnClickListener(this)
        }


        override fun onClick(v: View) {

            if (v.id == itemView.alarmToggleBtn.id)  {
                alarmToggleBtnClickListener.onAlarmToggleBtnClick(alarmId, adapterPosition)
                return
            }

            alarmClickListener.onAlarmClick(alarmId, adapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }


    interface AlarmClickListener {
        fun onAlarmClick(alarm_id: Int, position: Int)
    }

    interface AlarmLongClickListener {
        fun onLongAlarmClick(alarm_id: Int, position: Int)
    }

    interface AlarmToggleBtnClickListener {
        fun onAlarmToggleBtnClick(alarm_id: Int, position: Int)
    }

   fun setAlarmClickListener(listener: AlarmClickListener) {
        alarmClickListener = listener
    }

   fun setAlarmLongClickListener(listener: AlarmLongClickListener) {

    }

    fun setAlarmToggleBtnClickListener(listener: AlarmToggleBtnClickListener) {
        alarmToggleBtnClickListener = listener
    }

}