package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.*
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.list_item.*


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


        btPutValue.setOnClickListener {
            var providerValue: Float? = 0f
            "https://api.bitvalor.com/v1/order_book_stats.json".httpGet().responseObject(ProvidersList.Deserializer()){ request, response, result ->
                val (providersResult, err) = result
                when(spProvider.selectedItemPosition){
                    0 -> providerValue = providersResult?.FOX?.ask
                    1 -> providerValue = providersResult?.MBT?.ask
                    2 -> providerValue = providersResult?.NEG?.ask
                    3 -> providerValue = providersResult?.B2U?.ask
                    4 -> providerValue = providersResult?.BTD?.ask
                    5 -> providerValue = providersResult?.FLW?.ask
                    6 -> providerValue = providersResult?.LOC?.ask
                    7 -> providerValue = providersResult?.ARN?.ask
                }
                Toast.makeText(this, providerValue.toString(), Toast.LENGTH_LONG).show()

                etValue.setText(providerValue.toString())

            }
        }


    }

    data class ProvidersList(val FOX: ProviderItem,
                             val MBT: ProviderItem,
                             val NEG: ProviderItem,
                             val B2U: ProviderItem,
                             val BTD: ProviderItem,
                             val FLW: ProviderItem,
                             val LOC: ProviderItem,
                             val ARN: ProviderItem){

        class Deserializer: ResponseDeserializable<ProvidersList>{
            override fun deserialize(content: String): ProvidersList? = Gson().fromJson(content, ProvidersList::class.java)
        }
    }
    data class ProviderItem(val ask: Float)

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
