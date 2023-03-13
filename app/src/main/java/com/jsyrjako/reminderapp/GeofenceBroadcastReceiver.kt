package com.jsyrjako.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("GeofenceBroadcastReceiver", "GeofenceBroadcastReceiver onReceive")

        if (context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            val geofencingTransition = geofencingEvent.geofenceTransition

            val triggeringGeofences = geofencingEvent.triggeringGeofences

            println("Triggering geofences: $triggeringGeofences")

            println("Geofence transition: $geofencingTransition")

            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                // create notification
                val notificationHelper = NotificationHelper(context)
                val notification = notificationHelper.createNotification("Reminder", "You are near your reminder location")
                notificationHelper.manager.notify(1, notification)
                Log.d("GeofenceBroadcastReceiver", "Notify")

            } else if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                Log.d("GeofenceBroadcastReceiver", "Cancel")
            }
        }
    }
}