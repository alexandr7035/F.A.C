package com.alexandr7035.fac.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarms")
class AlarmEntity(@PrimaryKey(autoGenerate = true)
                  var id: Int = 0,
                  var name: String = "",
                  var hours: Int = 0,
                  var minutes: Int = 0,
                  var task_id: Int = 0,
                  var enabled: Boolean = true) {


    // To simplify debug process
    override fun toString(): String {

        var s = "[id=" + id + ","
        s += "name='" + name + "',"
        s += "hours=" + hours + ","
        s += "minutes=" + minutes + ","
        s += "task_id=" + task_id + ","
        s += "enabled='" + enabled + "']"

        return s
    }


}
