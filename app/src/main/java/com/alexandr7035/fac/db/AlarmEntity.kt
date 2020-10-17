package com.alexandr7035.fac.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarms")
class AlarmEntity(@PrimaryKey(autoGenerate = true)
                  var id: Int = 0,
                  var name: String,
                  var time: Long = 0)
