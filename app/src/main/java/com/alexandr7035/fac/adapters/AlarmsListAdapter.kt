package com.alexandr7035.fac.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandr7035.fac.R
import com.alexandr7035.fac.db.AlarmEntity
import kotlinx.android.synthetic.main.view_alarm.view.*

class AlarmsListAdapter : RecyclerView.Adapter<AlarmsListAdapter.ViewHolder>() {

    private var items: List<AlarmEntity> = ArrayList()

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
        holder.itemView.nameView.text = items.get(position).name

        holder.itemView.toggleBtn.setImageResource(R.drawable.ic_alarm_clock_off)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }



}