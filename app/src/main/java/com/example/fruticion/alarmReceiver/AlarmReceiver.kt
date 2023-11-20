package com.example.fruticion.alarmReceiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fruticion.R
import com.example.fruticion.activity.HomeActivity
import com.example.fruticion.fragments.DetailFragment
import com.example.fruticion.model.Fruit

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID=1
    }
    override fun onReceive(context: Context, p1: Intent?) {
        val fruit = p1?.getStringExtra("FRUIT_NAME")
        createSimpleNotification(fruit, context)
    }

    private fun createSimpleNotification(fruitName : String?, context: Context) {
        Log.d("AlarmNotification", "Estamos dentro de la creacion: ")
        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification = NotificationCompat.Builder(context, DetailFragment.MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.corazon)
            .setContentTitle("Frutición")
            .setContentText("Recordatorio de consumición")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("¡Ya es la hora! come tu $fruitName !")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        //prueba
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
        Log.d("AlarmNotification", "Llamado a notificar: ")
    }
}
