package com.example.ragabuza.bitcoinmonitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.Alarm
import com.example.ragabuza.bitcoinmonitor.model.AlarmType
import com.example.ragabuza.bitcoinmonitor.model.Condition
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpinners()

        btAdd.setOnClickListener{
            val alarm: Alarm = Alarm(1, etValue.text.toString().toLong(), Condition.GREATER, "FoxBit", 30, AlarmType.LOUD)
            val dao = AlarmDAO(this)
            dao.add(alarm)
            dao.close()

            var alarms = dao.getAlarm()

            alarms[1]
        }
    }

    fun initSpinners(){
        val condition = ArrayList<String>()
        condition.add(getString(R.string.Condition1))
        condition.add(getString(R.string.Condition2))
        initSpinner(spCondition, condition)

        val providers = ArrayList<String>()
        providers.add("FoxBit")
        providers.add("Mercado Bitcoin")
        providers.add("Negocie Coins")
        providers.add("BitcoinToYou")
        providers.add("BitcoinTrade")
        providers.add("flowBTC")
        providers.add("LocalBitcoins")
        providers.add("Arena Bitcoin")
        initSpinner(spProvider, providers)

        val notifyTimer = ArrayList<String>()
        notifyTimer.add("15 "+getString(R.string.Minute)+"s")
        notifyTimer.add("30 "+getString(R.string.Minute)+"s")
        notifyTimer.add("1 "+getString(R.string.Hour))
        notifyTimer.add("6 "+getString(R.string.Hour)+"s")
        notifyTimer.add("12 "+getString(R.string.Hour)+"s")
        notifyTimer.add("24 "+getString(R.string.Hour)+"s")
        initSpinner(spNotifyTimer, notifyTimer)

        val notifyType = ArrayList<String>()
        notifyType.add(getString(R.string.Simple))
        notifyType.add(getString(R.string.Sound))
        notifyType.add(getString(R.string.Loud))
        notifyType.add(getString(R.string.Ringer))
        initSpinner(spNotifyType, notifyType)
    }

    fun initSpinner(spinner: Spinner, list: ArrayList<String>){
        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


}
