package com.example.ragabuza.bitcoinmonitor.util

import android.app.Notification
import com.example.ragabuza.bitcoinmonitor.model.Alarm
import com.example.ragabuza.bitcoinmonitor.model.AlarmType
import com.example.ragabuza.bitcoinmonitor.model.Condition

/**
 * Created by diego.moyses on 12/12/2017.
 */


class AlarmInterpreter(val alarm: Alarm, val value: Float?) {

    var message :String = "ERROR: Not Happens"

    fun happens(): Boolean{
        if (value!=null) {
            if (alarm.condition == Condition.GREATER && alarm.value <= value) {
                message = "${alarm.provider}: $value"
                return true
            }
            if (alarm.condition == Condition.LESSER && alarm.value >= value) {
                message = "${alarm.provider}: $value"
                return true
            }
        }
        return false
    }
    fun interpret(): AlarmType?{
        if (!happens()) return null
        return alarm.type
    }
}