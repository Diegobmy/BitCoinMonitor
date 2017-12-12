package com.example.ragabuza.bitcoinmonitor.util

import android.app.AlertDialog
import android.content.Context
import android.os.Vibrator
import android.content.DialogInterface




/**
 * Created by diego.moyses on 12/12/2017.
 */
class VibrationManager(val context: Context?) {

    val v: Vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun startVibration(){
        if (context == null) return

        v.vibrate(1000*15)
    }
    fun stopVibration(){
        v.cancel()
    }
}

