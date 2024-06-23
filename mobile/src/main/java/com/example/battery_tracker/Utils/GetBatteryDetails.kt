package com.example.battery_tracker.Utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.example.battery_tracker.R
import com.google.android.gms.wearable.Wearable

object GetBatteryDetails {
    fun getHealthData(health: Int): String {
        var healthstate: String = "null"

        when (health) {
            BatteryManager.BATTERY_HEALTH_GOOD -> healthstate = "Good"
            BatteryManager.BATTERY_HEALTH_UNKNOWN -> healthstate = "Unknown"
            BatteryManager.BATTERY_HEALTH_COLD -> healthstate = "Cold"
            BatteryManager.BATTERY_HEALTH_DEAD -> healthstate = "Dead"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> healthstate = "Over Heat"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> healthstate = "Over Voltage"


        }
        return healthstate

    }

    fun getChargingStatus(status: Int): String {
        var chargingstatus by mutableStateOf("null")
        when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> chargingstatus = "Charging"
            BatteryManager.BATTERY_STATUS_FULL -> chargingstatus = "Full"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> chargingstatus = "Discharging"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> chargingstatus = "Not Charging"
        }
        return chargingstatus
    }

    fun getPlugged(plugged: Int): String {
        var typeplug: String = "null"
        when (plugged) {
            BatteryManager.BATTERY_PLUGGED_AC -> typeplug = "AC"
            BatteryManager.BATTERY_PLUGGED_USB -> typeplug = "USB"
            BatteryManager.BATTERY_PLUGGED_DOCK -> typeplug = "Dock"
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> typeplug = "Wireless"

        }
        return typeplug
    }
    fun getWearOsString(context: Context):String{
        var wearosData by mutableStateOf("")
        Wearable.getMessageClient(context).addListener {
            wearosData = String(it.data)
        }
        return wearosData
    }

    fun getWearosBattery(wearosData: String):String{
        return  wearosData.substringBefore(AppConstants.WEAROS_CHARGING_DIVIDER)
    }

    fun getWearosChargingDetails(wearosData: String): Boolean{
        return wearosData.substringAfter(AppConstants.WEAROS_CHARGING_DIVIDER).toBoolean()
    }

    fun showLowBatteryNotification(batteryLevel: String,context:Context) {
        val sharedPref = SharedPref.getInstance(context)
        sharedPref.isNotificationSent=true

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "battery_low_channel"

        val channel = NotificationChannel(
            channelId,
            "Low Battery Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.charging)
            .setContentTitle("Low Wear OS Battery")
            .setContentText("Wear OS battery is at $batteryLevel%. Please charge your device.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}