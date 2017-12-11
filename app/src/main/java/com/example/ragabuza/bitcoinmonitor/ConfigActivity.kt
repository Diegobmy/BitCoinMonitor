package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ragabuza.bitcoinmonitor.util.PrefManager
import kotlinx.android.synthetic.main.activity_config.*

/**
 * Created by diego.moyses on 12/11/2017.
 */
class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


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
            PrefManager(this).putTime(tvNotifyTimer.text.toString().toInt())
            PrefManager(this).putTimeFormat(spNotifyTimer.selectedItem.toString())
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }


    }
}