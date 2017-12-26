package com.example.ragabuza.bitcoinmonitor

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.Providers
import com.example.ragabuza.bitcoinmonitor.model.ProvidersList
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_trends.*
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.app.ProgressDialog
import android.opengl.Visibility
import android.view.View.GONE


/**
 * Created by diego.moyses on 12/12/2017.
 */
class TrendsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends)
        this.supportActionBar!!.title = "Trends"
        initFoot()

        val progress = ProgressDialog(this)
        progress.setTitle("Carregando")
        progress.setMessage("Carregando dados.")
        progress.setCancelable(false)
        progress.show()

        val dao = AlarmDAO(this)
        val alarms = dao.getAlarm()
        dao.close()


        "https://api.bitvalor.com/v1/order_book_stats.json".httpGet().responseObject(ProvidersList.Deserializer()) { request, response, result ->
            val (providersResult, err) = result

            if (providersResult?.FOX?.ask == null) FOX.visibility = GONE
            var spannable = SpannableString(Providers.FOX.nome + ":\n R$ "+providersResult?.FOX?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.FOX.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            FOX.text = spannable

            if (providersResult?.MBT?.ask == null) MBT.visibility = GONE
            spannable = SpannableString(Providers.MBT.nome + ":\n R$ "+providersResult?.MBT?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.MBT.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            MBT.text = spannable

            if (providersResult?.NEG?.ask == null) NEG.visibility = GONE
            spannable = SpannableString(Providers.NEG.nome + ":\n R$ "+providersResult?.NEG?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.NEG.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            NEG.text = spannable

            if (providersResult?.B2U?.ask == null) B2U.visibility = GONE
            spannable = SpannableString(Providers.B2U.nome + ":\n R$ "+providersResult?.B2U?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.B2U.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            B2U.text = spannable

            if (providersResult?.BTD?.ask == null) BTD.visibility = GONE
            spannable = SpannableString(Providers.BTD.nome + ":\n R$ "+providersResult?.BTD?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.BTD.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            BTD.text = spannable

            if (providersResult?.FLW?.ask == null) FLW.visibility = GONE
            spannable = SpannableString(Providers.FLW.nome + ":\n R$ "+providersResult?.FLW?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.FLW.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            FLW.text = spannable

            if (providersResult?.ARN?.ask == null) ARN.visibility = GONE
            spannable = SpannableString(Providers.ARN.nome + ":\n R$ "+providersResult?.ARN?.ask)
            spannable.setSpan( StyleSpan(Typeface.BOLD),0,Providers.ARN.nome.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ARN.text = spannable

            progress.dismiss()
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

        btConfig.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

    }
}