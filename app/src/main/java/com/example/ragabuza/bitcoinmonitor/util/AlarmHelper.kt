package com.example.ragabuza.bitcoinmonitor.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Context.ALARM_SERVICE
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.AlarmReceiver


/**
 * Created by diego.moyses on 12/11/2017.
 */
class AlarmHelper(private val context: Context?) {

    private val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
    private val alarmIntent = Intent(context, AlarmReceiver::class.java)
    private val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

    fun setAlarm(){
        val time = PrefManager(context).getTime()!!.toLong() * 1000 * 60 * if(PrefManager(context).getTimeFormat() == "Horas") 60 else 1
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, time, pendingIntent)
        Toast.makeText(context, "Alarme acionado.", Toast.LENGTH_LONG).show()
    }

    fun stopAlarm(){
        alarmManager.cancel(pendingIntent)
    }

}