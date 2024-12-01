package com.example.agenda_pf

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("notification_message") ?: "Tienes un recordatorio"
        val notificationId = System.currentTimeMillis().toInt()

        // Crear canal de notificación para Android 8.0+ (API 26+)
        val channelId = "task_reminder_channel"
        val channelName = "Recordatorios de Tareas"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones para recordatorios de tareas"
            }
            notificationManager.createNotificationChannel(channel) // Crear el canal
        }

        // Verificar permiso de notificaciones (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !NotificationManagerCompat.from(context).areNotificationsEnabled()
        ) {
            Log.w("NotificationReceiver", "No se pueden mostrar notificaciones: permiso no otorgado")
            return
        }

        // Crear la notificación
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono de la notificación
            .setContentTitle("Recordatorio") // Título de la notificación
            .setContentText(message) // Texto de la notificación
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad ALTA
            .setAutoCancel(true) // Descartar notificación al tocarla
            .build()

        // Mostrar la notificación
        notificationManager.notify(notificationId, notification)
        Log.d("NotificationReceiver", "Notificación enviada: $message con ID $notificationId")
    }
}
