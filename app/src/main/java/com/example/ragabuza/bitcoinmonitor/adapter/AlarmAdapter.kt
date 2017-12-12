package com.example.ragabuza.bitcoinmonitor.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ragabuza.bitcoinmonitor.model.Alarm
import android.view.LayoutInflater
import android.widget.ImageButton
import com.example.ragabuza.bitcoinmonitor.R
import android.widget.TextView
import com.example.ragabuza.bitcoinmonitor.AlarmActivity
import com.example.ragabuza.bitcoinmonitor.AlarmReceiver
import com.example.ragabuza.bitcoinmonitor.ListActivity
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO


/**
 * Created by diego on 11/12/2017.
 */
class AlarmAdapter(val context: Context, val alarms: MutableList<Alarm>) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        val alarm: Alarm = alarms[p0]

        val inflater = LayoutInflater.from(context)
        var view = p1
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, p2, false)
        }

        val fieldValue = view?.findViewById<TextView>(R.id.tvValue)
        fieldValue?.text = alarm.value.toString()

        val fieldProvider = view?.findViewById<TextView>(R.id.tvProvider)
        fieldProvider?.text = alarm.provider.nome

        val buttonDelete = view?.findViewById<ImageButton>(R.id.btDelete)
        buttonDelete?.setOnClickListener{
            val dao = AlarmDAO(context)
            dao.del(alarm)
            dao.close()
            alarms.remove(alarm)
            this.notifyDataSetChanged()
        }

        val buttonEdit = view?.findViewById<ImageButton>(R.id.btEdit)
        buttonEdit?.setOnClickListener{
            val intent = Intent(context, AlarmActivity::class.java)
            intent.putExtra("alarm", alarm)
            context.startActivity(intent)
        }


        return view
    }

    override fun getItem(p0: Int): Any {
        return alarms[p0]
    }

    override fun getItemId(p0: Int): Long {
        return alarms[p0].id
    }

    override fun getCount(): Int {
        return alarms.size
    }
}
