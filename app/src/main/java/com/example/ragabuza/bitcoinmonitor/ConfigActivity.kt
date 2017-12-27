package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.util.AlarmHelper
import com.example.ragabuza.bitcoinmonitor.util.PrefManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
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

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdFailedToLoad(errorCode : Int) {
                Toast.makeText(this@ConfigActivity, errorCode.toString(), Toast.LENGTH_LONG).show()
            }
        }

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
