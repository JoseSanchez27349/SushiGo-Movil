package com.example.clinetesushigo.data.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "sushigo_orders"
    private const val CHANNEL_NAME = "Pedidos SushiGo"
    private const val PREFS_NAME = "sushigo_notifications"

    // Crear canal de notificaciones
    fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val vibrationPattern = longArrayOf(
                0,
                250,
                150,
                250
            )

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones sobre el estado de tus pedidos"
                enableVibration(true)
                vibrationPattern?.let {
                    setVibrationPattern(it)
                }
            }

            val manager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    // Mostrar notificación cuando el pedido esté listo
    fun showOrderReady(
        context: Context,
        orderId: String,
        orderNumber: String
    ) {

        if (orderId.isBlank()) return

        // Verificar permiso de notificaciones
        if (
            Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return
        }

        // Evitar notificaciones duplicadas
        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val notificationKey = "ready_$orderId"

        if (preferences.getBoolean(notificationKey, false)) {
            return
        }

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)

        val pendingIntent = launchIntent?.let {
            PendingIntent.getActivity(
                context,
                orderId.hashCode(),
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(com.example.clinetesushigo.R.mipmap.ic_launcher)
            .setContentTitle("¡Tu pedido está listo! 🍣")
            .setContentText(
                "Ya puedes pasar a recogerlo. ¡Gracias por elegir SushiGo!"
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "¡Tu pedido está listo! Ya puedes pasar a recogerlo. " +
                                "¡Gracias por elegir SushiGo!"
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 250, 150, 250))
            .apply {
                pendingIntent?.let {
                    setContentIntent(it)
                }
            }
            .build()

        try {
            NotificationManagerCompat.from(context).notify(
                orderId.hashCode(),
                notification
            )

            preferences.edit()
                .putBoolean(notificationKey, true)
                .apply()

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}