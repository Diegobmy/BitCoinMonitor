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

    var name :String = when (alarm.provider.ordinal) {
        0 -> "FoxBit"
        1 -> "Mercado Bitcoin"
        2 -> "Negocie Coins"
        3 -> "BitcoinToYou"
        4 -> "BitcoinTrade"
        5 -> "flowBTC"
        6 -> "LocalBitcoins"
        7 -> "Arena Bitcoin"
        else -> ""
    }

    fun happens(): Boolean{
        if (value!=null) {
            if (alarm.condition == Condition.GREATER && alarm.value <= value) {
                message = "$name \n   Alarme: Maior que R\$ ${alarm.value} \n   Valor Atual: R$ $value"
                message = message.substring(0,message.length - 2)
                return true
            }
            if (alarm.condition == Condition.LESSER && alarm.value >= value) {
                message = "$name \n Alarme: Menor que ${alarm.value} \n Valor Atual: $value"
                message = message.substring(0,message.length - 2)
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