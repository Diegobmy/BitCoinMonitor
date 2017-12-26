package com.example.ragabuza.bitcoinmonitor.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ragabuza.bitcoinmonitor.model.Alarm
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import com.example.ragabuza.bitcoinmonitor.R
import android.widget.TextView
import com.example.ragabuza.bitcoinmonitor.AlarmActivity
import com.example.ragabuza.bitcoinmonitor.AlarmReceiver
import com.example.ragabuza.bitcoinmonitor.ListActivity
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.Condition
import android.widget.Toast
import android.content.DialogInterface




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
            AlertDialog.Builder(context)
                .setTitle("Confirmar exclusão")
                .setMessage("Você realmente quer excluir este alarme?")
                .setPositiveButton("Sim", DialogInterface.OnClickListener {
                    dialog, whichButton ->
                    val dao = AlarmDAO(context)
                    dao.del(alarm)
                    dao.close()
                    alarms.remove(alarm)
                    this.notifyDataSetChanged()
                })
                .setNegativeButton("Cancelar", null).show()
        }

        val buttonEdit = view?.findViewById<ImageButton>(R.id.btEdit)
        buttonEdit?.setOnClickListener{
            val intent = Intent(context, AlarmActivity::class.java)
            intent.putExtra("alarm", alarm)
            context.startActivity(intent)
        }

        val icon = view?.findViewById<ImageView>(R.id.ivArrow)
        icon?.setImageResource(if (alarm.condition == Condition.GREATER)
            R.drawable.ic_keyboard_arrow_up
        else
            R.drawable.ic_keyboard_arrow_down)


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
