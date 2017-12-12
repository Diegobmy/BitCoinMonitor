package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.util.AlarmHelper
import com.example.ragabuza.bitcoinmonitor.util.PrefManager
import kotlinx.android.synthetic.main.activity_config.*

/**
 * Created by diego.moyses on 12/11/2017.
 */
class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        initFoot()

        tvNotifyTimer.setText(PrefManager(this).getTime().toString())


        val notifyTimer = ArrayList<String>()
        notifyTimer.add(getString(R.string.Minute)+"s")
        notifyTimer.add(getString(R.string.Hour)+"s")
        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, notifyTimer)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = spNotifyTimer as Spinner
        spinner.adapter = adapter

        if(PrefManager(this).getTimeFormat() == "Horas") spNotifyTimer.setSelection(1)

        btApply.setOnClickListener {
            val dao = AlarmDAO(this)

            PrefManager(this).putTime(tvNotifyTimer.text.toString().toInt())
            PrefManager(this).putTimeFormat(spNotifyTimer.selectedItem.toString())

            AlarmHelper(this).stopAlarm()
            if(!dao.getAlarm().isEmpty()) AlarmHelper(this).setAlarm()

            dao.close()

            onBackPressed()
        }

        }

    fun initFoot(){
        btNotifications.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        btNew.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        btTrends.setOnClickListener {
            val intent = Intent(this, TrendsActivity::class.java)
            startActivity(intent)
        }

    }

    }
