package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.*
import com.example.ragabuza.bitcoinmonitor.util.PrefManager
import kotlinx.android.synthetic.main.activity_alarm.*


class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        initSpinners()

        btAdd.setOnClickListener {
            val alarm: Alarm = Alarm(
                    1,
                    etValue.text.toString().toLong(),
                    Condition.values()[spCondition.selectedItemPosition],
                    Providers.values()[spProvider.selectedItemPosition].toString(),
                    AlarmType.values()[spNotifyType.selectedItemPosition])
            val dao = AlarmDAO(this)
            dao.add(alarm)
            dao.close()
        }

        btNotifications.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        btTrends.setOnClickListener{
            AlarmHelper(this).setAlarm()
        }

        btConfig.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }
    }

    fun initSpinners(){
        val condition = ArrayList<String>()
        condition.add(getString(R.string.Condition1))
        condition.add(getString(R.string.Condition2))
        initSpinner(spCondition, condition)

        val providers = ArrayList<String>()
        providers.add(Providers.FOX.nome)
        providers.add(Providers.MBT.nome)
        providers.add(Providers.NEG.nome)
        providers.add(Providers.B2U.nome)
        providers.add(Providers.BTD.nome)
        providers.add(Providers.FLW.nome)
        providers.add(Providers.LOC.nome)
        providers.add(Providers.ARN.nome)
        initSpinner(spProvider, providers)

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
