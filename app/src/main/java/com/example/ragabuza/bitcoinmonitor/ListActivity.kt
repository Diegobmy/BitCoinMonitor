package com.example.ragabuza.bitcoinmonitor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.adapter.AlarmAdapter
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import kotlinx.android.synthetic.main.activity_list.*

/**
 * Created by diego on 11/12/2017.
 */
class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

//        lvAlarms.onItemClickListener = AdapterView.OnItemClickListener{
//            Toast.makeText(this, )
            //TODO delete
//        }
//        listaAlunos!!.onItemClickListener = AdapterView.OnItemClickListener { lista, item, position, id ->
//            val aluno = listaAlunos!!.getItemAtPosition(position) as Aluno
//
//            val intentVaiProFormulario = Intent(this@ListaAlunosActivity, FormularioActivity::class.java)
//            intentVaiProFormulario.putExtra("aluno", aluno)
//            startActivity(intentVaiProFormulario)
//        }

    }

    private fun loadList() {
        val dao = AlarmDAO(this)
        val alarms = dao.getAlarm()
        dao.close()

        val adapter = AlarmAdapter(this, alarms)
        lvAlarms.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }


}
