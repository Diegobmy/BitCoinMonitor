package com.example.ragabuza.bitcoinmonitor

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.support.v4.app.NotificationCompat


/**
 * Created by diego.moyses on 12/11/2017.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {



        val mNotificationManager = p0?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(p0)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bitcoin")
                .setContentText("AAAAAAAAAAAAAAAAAAAAAA")
                .setDefaults(Notification.DEFAULT_SOUND)

        val intentDestination = Intent(p0, AlarmActivity::class.java)

        val pi = PendingIntent.getActivity(p0, 0, intentDestination, 0)
        mBuilder.setContentIntent(pi)

        val mNotification = mBuilder.build()
        mNotification.flags = mNotification.flags or (Notification.FLAG_AUTO_CANCEL or Notification.DEFAULT_SOUND)

        mNotificationManager.notify(0, mNotification)

        AlarmHelper(p0).stopAlarm()


    }


}