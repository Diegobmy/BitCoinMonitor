package com.example.ragabuza.bitcoinmonitor

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ClipDescription
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import com.example.ragabuza.bitcoinmonitor.dao.AlarmDAO
import com.example.ragabuza.bitcoinmonitor.model.AlarmType
import com.example.ragabuza.bitcoinmonitor.model.ProvidersList
import com.example.ragabuza.bitcoinmonitor.util.AlarmHelper
import com.example.ragabuza.bitcoinmonitor.util.AlarmInterpreter
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_alarm.*
import android.R.raw
import android.media.MediaPlayer
import android.os.Vibrator
import android.support.v4.content.ContextCompat.startActivity
import com.example.ragabuza.bitcoinmonitor.util.VibrationManager


/**
 * Created by diego.moyses on 12/11/2017.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val dao = AlarmDAO(p0)
        val alarms = dao.getAlarm()
        dao.close()

        if (alarms.isEmpty()) AlarmHelper(p0).stopAlarm()
        else {
            alarms.forEach { a ->
                var providerValue: Float? = 0f
                "https://api.bitvalor.com/v1/order_book_stats.json".httpGet().responseObject(ProvidersList.Deserializer()) { request, response, result ->
                    val (providersResult, err) = result
                    when (a.provider.ordinal) {
                        0 -> providerValue = providersResult?.FOX?.ask
                        1 -> providerValue = providersResult?.MBT?.ask
                        2 -> providerValue = providersResult?.NEG?.ask
                        3 -> providerValue = providersResult?.B2U?.ask
                        4 -> providerValue = providersResult?.BTD?.ask
                        5 -> providerValue = providersResult?.FLW?.ask
                        6 -> providerValue = providersResult?.LOC?.ask
                        7 -> providerValue = providersResult?.ARN?.ask
                    }

                    val interpreter = AlarmInterpreter(a, providerValue)

                    when (interpreter.interpret()) {
                        AlarmType.SIMPLE -> {
                            showNotification(a.id.toInt(), p0, 0 ,interpreter.message)
                        }
                        AlarmType.SOUND -> {
                            showNotification(a.id.toInt(), p0, Notification.DEFAULT_ALL ,interpreter.message)
                        }
                        AlarmType.LOUD -> {
                            showNotification(a.id.toInt(), p0, Notification.DEFAULT_VIBRATE ,interpreter.message)
                            val mp = MediaPlayer.create(p0, R.raw.demonstrative)
                            mp.start()
                        }
                        AlarmType.RINGER -> {
//                            showNotification(p0, Notification.rin ,interpreter.message)
                        }
                    }

                }
            }
        }

    }

    fun showNotification(id: Int, p0: Context?, type: Int, description: String){

        val mNotificationManager = p0?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(p0)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bitcoin")
                .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                .setContentText(description)
                .setDefaults(type)
        val intentDestination = Intent(p0, ListActivity::class.java)
        val pi = PendingIntent.getActivity(p0, 0, intentDestination, 0)
        mBuilder.setContentIntent(pi)
        val mNotification = mBuilder.build()
        mNotification.flags = mNotification.flags or (Notification.FLAG_AUTO_CANCEL or Notification.DEFAULT_SOUND)
        mNotificationManager.notify(id, mNotification)

    }

}