package com.example.ragabuza.bitcoinmonitor.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by diego.moyses on 12/11/2017.
 */
class PrefManager(val context: Context) {

    private val PREF_NAME = "feed_preferences"
    private val ALARM_TIME = "alarm_time"
    private val ALARM_TIME_FORMAT = "alarm_time_format"

    private fun get(): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getTime(): Int {
        return get().getInt(ALARM_TIME, 15)
    }

    fun putTime(time: Int) {
        get().edit().putInt(ALARM_TIME, time).apply()
    }


    fun getTimeFormat(): String {
        return get().getString(ALARM_TIME_FORMAT, "Minutos")
    }

    fun putTimeFormat(time: String) {
        get().edit().putString(ALARM_TIME_FORMAT, time).apply()
    }

}