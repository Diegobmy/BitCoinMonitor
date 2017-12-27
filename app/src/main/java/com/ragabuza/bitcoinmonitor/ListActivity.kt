package com.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ragabuza.bitcoinmonitor.adapter.AlarmAdapter
import com.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.ragabuza.bitcoinmonitor.util.VibrationManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_list.*

/**
 * Created by diego on 11/12/2017.
 */
class ListActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        this.supportActionBar!!.title = "Seus alarmes"
        this.supportActionBar?.setDisplayUseLogoEnabled(true)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.bitcoin_clock)
        initFoot()

        mAdView = findViewById(R.id.adViewList)
        val adRequest = AdRequest.Builder()
                .build()
        mAdView.loadAd(adRequest)

    }

    private fun loadList() {
        val dao = AlarmDAO(this)
        val alarms = dao.getAlarm()
        dao.close()

        val MutAlarms = alarms.toMutableList()

        val adapter = AlarmAdapter(this, MutAlarms)
        lvAlarms.adapter = adapter

    }

    override fun onBackPressed() {

    }

    override fun onResume() {
        super.onResume()
        loadList()
        VibrationManager(this).stopVibration()
    }
    fun initFoot(){
        btNew.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        btConfig.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

        btTrends.setOnClickListener {
            val intent = Intent(this, TrendsActivity::class.java)
            startActivity(intent)
        }

    }

}
