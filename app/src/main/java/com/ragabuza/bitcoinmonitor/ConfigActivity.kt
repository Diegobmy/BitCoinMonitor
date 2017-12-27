package com.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.ragabuza.bitcoinmonitor.util.AlarmHelper
import com.ragabuza.bitcoinmonitor.util.PrefManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_config.*

/**
 * Created by diego.moyses on 12/11/2017.
 */
class ConfigActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        this.supportActionBar!!.title = "Configurações"
        this.supportActionBar?.setDisplayUseLogoEnabled(true)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.bitcoin_clock)

        initFoot()

        mAdView = findViewById(R.id.adViewConfig)
        val adRequest = AdRequest.Builder()
                .build()
        mAdView.loadAd(adRequest)

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
          setConfig()
        }

        }

    fun setConfig(){
        val dao = AlarmDAO(this)


        if (tvNotifyTimer.text.toString().isEmpty()) {
            Toast.makeText(this, "Valor de verificação vazio.", Toast.LENGTH_LONG).show()
            return
        }

        if (!tvNotifyTimer.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())) {
            Toast.makeText(this, "Valor de verificação não numérico.", Toast.LENGTH_LONG).show()
            return
        }

        if (tvNotifyTimer.text.toString().toFloat() > 59 && spNotifyTimer.selectedItem.toString() == "Minutos") {
            Toast.makeText(this, "Valor de verificação muito alto.", Toast.LENGTH_LONG).show()
            return
        }

        if (tvNotifyTimer.text.toString().toFloat() < 3 && spNotifyTimer.selectedItem.toString() == "Minutos") {
            Toast.makeText(this, "Valor de verificação muito baixo.", Toast.LENGTH_LONG).show()
            return
        }

        if (tvNotifyTimer.text.toString().toFloat() > 2880 && spNotifyTimer.selectedItem.toString() == "Horas") {
            Toast.makeText(this, "Valor de verificação muito alto.", Toast.LENGTH_LONG).show()
            return
        }

        PrefManager(this).putTime(tvNotifyTimer.text.toString().toInt())
        PrefManager(this).putTimeFormat(spNotifyTimer.selectedItem.toString())

        AlarmHelper(this).stopAlarm()
        if(!dao.getAlarm().isEmpty()) AlarmHelper(this).setAlarm()

        dao.close()

        onBackPressed()
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
