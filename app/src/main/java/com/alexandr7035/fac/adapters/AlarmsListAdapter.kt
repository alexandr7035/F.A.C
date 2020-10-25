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
        val time = "" + items[position].hours + ":" + items[position].minutes
        holder.itemView.timeView.text = time

        // Set icon to clock btn
        if (items[position].enabled){
            holder.itemView.toggleBtn.setImageResource(R.drawable.ic_alarm_clock_off)
        }
        else {
            holder.itemView.toggleBtn.setImageResource(R.drawable.ic_alarm_clock)
        }

        Log.d(LOG_TAG, holder.itemView.isEnabled.toString())

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }



}