package com.example.ragabuza.bitcoinmonitor


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.*
import com.example.ragabuza.bitcoinmonitor.util.AlarmHelper
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_alarm.*



class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        this.supportActionBar!!.title = "Nova notificação"
        initSpinners()

        val editAlarm: Alarm? = intent.getSerializableExtra("alarm") as Alarm?

        if(editAlarm != null){
            fill(editAlarm)
        }

        btAdd.setOnClickListener {
            setOrUpdateAlarm(editAlarm)
        }
        btCancel.setOnClickListener {
            onBackPressed()
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
                    6 -> providerValue = providersResult?.ARN?.ask
                }

                var text = providerValue.toString().split(".")

                Toast.makeText(this, text[0], Toast.LENGTH_LONG).show()

                etValue.setText(text[0])

            }
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
        providers.add(Providers.ARN.nome)
        initSpinner(spProvider, providers)

        val notifyType = ArrayList<String>()
        notifyType.add(getString(R.string.Simple))
        notifyType.add(getString(R.string.Sound))
        notifyType.add(getString(R.string.Loud))
//        notifyType.add(getString(R.string.Ringer))
        initSpinner(spNotifyType, notifyType)
    }

    fun initSpinner(spinner: Spinner, list: ArrayList<String>){
        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun fill(alarm: Alarm){
        etValue.setText(alarm.value.toString())
        spCondition.setSelection(alarm.condition.ordinal)
        spProvider.setSelection(alarm.provider.ordinal)
        spNotifyType.setSelection(alarm.type.ordinal)
        this.supportActionBar!!.title = "Editar notificação"
        btAdd.text = "Salvar"
    }

    fun setOrUpdateAlarm(eAlarm: Alarm?){

        if (etValue.text.toString().isEmpty()) {
            Toast.makeText(this, "Valor de trading vazio.", Toast.LENGTH_LONG).show()
            return
        }

        if (!etValue.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())) {
            Toast.makeText(this, "Valor de trading não numérico.", Toast.LENGTH_LONG).show()
            return
        }

        if (etValue.text.toString().toLong() > 999999) {
            Toast.makeText(this, "Valor de trading muito alto.", Toast.LENGTH_LONG).show()
            return
        }


        val id = if (eAlarm == null) 1 else eAlarm.id

        val dao = AlarmDAO(this)
        if(dao.getAlarm().isEmpty()) AlarmHelper(this).setAlarm()
        val alarm: Alarm = Alarm(
                id,
                etValue.text.toString().toLong(),
                Condition.values()[spCondition.selectedItemPosition],
                Providers.values()[spProvider.selectedItemPosition],
                AlarmType.values()[spNotifyType.selectedItemPosition])

        if (eAlarm == null) dao.add(alarm) else dao.alt(alarm)

        dao.close()

        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)

    }


}
