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

            var id = ""
            var title = ""
            var text = ""
            var location_x = ""
            var location_y = ""


            //val triggeringGeofences = geofencingEvent.triggeringGeofences
            //println("Triggering geofences: $triggeringGeofences")
            //println("Geofence transition: $geofencingTransition")

            if (intent != null) {
                id = intent.getStringExtra("reminder_id").toString()
                title = intent.getStringExtra("reminder_title").toString()
                text = intent.getStringExtra("reminder_text").toString()
                location_x = intent.getStringExtra("reminder_location_x").toString()
                location_y = intent.getStringExtra("reminder_location_y").toString()
            }

            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                // create notification
                val notificationHelper = NotificationHelper(context)
                val notification = notificationHelper.createNotification("Reminder: $title is near you", "$text \nLat:$location_x Lng:$location_y")
                notificationHelper.manager.notify(1, notification)
                Log.d("GeofenceBroadcastReceiver", "Notify")

            } else if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                Log.d("GeofenceBroadcastReceiver", "Cancel")
            }
        }
    }
}