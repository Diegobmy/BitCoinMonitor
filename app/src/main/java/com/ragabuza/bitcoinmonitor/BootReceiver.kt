package com.ragabuza.bitcoinmonitor

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.ragabuza.bitcoinmonitor.util.AlarmHelper


/**
 * Created by diego.moyses on 12/27/2017.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            AlarmHelper(context).setAlarm()
        }
    }
}