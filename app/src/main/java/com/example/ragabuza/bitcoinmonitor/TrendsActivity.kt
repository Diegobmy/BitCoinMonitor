package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.Providers
import com.example.ragabuza.bitcoinmonitor.model.ProvidersList
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_trends.*
import android.graphics.drawable.Drawable



/**
 * Created by diego.moyses on 12/12/2017.
 */
class TrendsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends)
        initFoot()

        val dao = AlarmDAO(this)
        val alarms = dao.getAlarm()
        dao.close()


        "https://api.bitvalor.com/v1/order_book_stats.json".httpGet().responseObject(ProvidersList.Deserializer()) { request, response, result ->
            val (providersResult, err) = result
            FOX.text = Providers.FOX.nome + ": R$ "+providersResult?.FOX?.ask
            MBT.text = Providers.MBT.nome + ": R$ "+providersResult?.MBT?.ask
            NEG.text = Providers.NEG.nome + ": R$ "+providersResult?.NEG?.ask
            B2U.text = Providers.B2U.nome + ": R$ "+providersResult?.B2U?.ask
            BTD.text = Providers.BTD.nome + ": R$ "+providersResult?.BTD?.ask
            FLW.text = Providers.FLW.nome + ": R$ "+providersResult?.FLW?.ask
            LOC.text = Providers.LOC.nome + ": R$ "+providersResult?.LOC?.ask
            ARN.text = Providers.ARN.nome + ": R$ "+providersResult?.ARN?.ask
        }

        val img = this.getResources().getDrawable(R.drawable.ic_alarm)
        img.setBounds(0, 0, 60, 60)

        alarms.forEach { a ->
            when(a.provider.ordinal){
                0 -> FOX.setCompoundDrawables(img, null, null, null)
                1 -> MBT.setCompoundDrawables(img, null, null, null)
                2 -> NEG.setCompoundDrawables(img, null, null, null)
                3 -> B2U.setCompoundDrawables(img, null, null, null)
                4 -> BTD.setCompoundDrawables(img, null, null, null)
                5 -> FLW.setCompoundDrawables(img, null, null, null)
                6 -> LOC.setCompoundDrawables(img, null, null, null)
                7 -> ARN.setCompoundDrawables(img, null, null, null)
            }
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