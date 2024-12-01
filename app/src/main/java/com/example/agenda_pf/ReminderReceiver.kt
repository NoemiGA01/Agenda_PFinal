package com.example.agenda_pf

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra("taskTitle") ?: return

        // Obtener el NotificationManager para gestionar las notificaciones
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generar un ID único para cada notificación
        val notificationId = Random.nextInt()

        // Crear la notificación
        val notification = NotificationCompat.Builder(context, "task_reminder_channel")
            .setContentTitle("Recordatorio: $taskTitle")
            .setContentText("¡Es hora de tu tarea!")
            .setSmallIcon(android.R.drawable.ic_dialog_alert) // Usa un ícono disponible
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Mostrar la notificación
        notificationManager.notify(notificationId, notification)
    }
}
