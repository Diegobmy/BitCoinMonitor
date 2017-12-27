package com.ragabuza.bitcoinmonitor.util

import android.content.Context
import android.os.Vibrator


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

