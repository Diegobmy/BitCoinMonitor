package com.example.ragabuza.bitcoinmonitor.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ragabuza.bitcoinmonitor.model.Alarm
import com.example.ragabuza.bitcoinmonitor.model.AlarmType
import com.example.ragabuza.bitcoinmonitor.model.Condition
import com.example.ragabuza.bitcoinmonitor.model.Providers
import java.util.ArrayList

class AlarmDAO(context: Context?) : SQLiteOpenHelper(context, "Alarm", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE Alarm (id INTEGER PRIMARY KEY, " +
                "value INTEGER NOT NULL, " +
                "condition INTEGER, " +
                "provider INTEGER, " +
                "type INTEGER);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        var sql = ""
//        when (oldVersion) {
//            1 -> {
//                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT"
//                db.execSQL(sql) // indo para versao 2
//            }
//        }

    }

    fun add(alarm: Alarm) {
        val db = writableDatabase

        val dados = getAlarmInfo(alarm)

        db.insert("Alarm", null, dados)
    }

    private fun getAlarmInfo(alarm: Alarm): ContentValues {
        val dados = ContentValues()
        dados.put("value", alarm.value)
        dados.put("condition", alarm.condition.ordinal)
        dados.put("provider", alarm.provider.ordinal)
        dados.put("type", alarm.type.ordinal)

        return dados
    }

        fun getAlarm(): List<Alarm> {
            val sql = "SELECT * FROM Alarm;"
        val db = readableDatabase
        val c = db.rawQuery(sql, null)

        val alarms = ArrayList<Alarm>()

        while (c.moveToNext()) {
            val alarm = Alarm(
            c.getLong(c.getColumnIndex("id")),
            c.getLong(c.getColumnIndex("value")),
            Condition.values()[c.getInt(c.getColumnIndex("condition"))],
            Providers.values()[c.getInt(c.getColumnIndex("provider"))],
            AlarmType.values()[c.getInt(c.getColumnIndex("type"))])

            alarms.add(alarm)
        }
        c.close()

        return alarms
    }

    fun del(alarm: Alarm) {
        val db = writableDatabase

        val params = arrayOf<String>(alarm.id.toString())
        db.delete("Alarm", "id = ?", params)
    }

    fun alt(alarm: Alarm) {
        val db = writableDatabase

        val dados = getAlarmInfo(alarm)

        val params = arrayOf<String>(alarm.id.toString())
        db.update("Alarm", dados, "id = ?", params)
    }
}
