package com.jsyrjako.reminderapp

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.jsyrjako.core.domain.entity.Reminder

class GeofenceHelper (private val context: Context) {

    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    @RequiresApi(Build.VERSION_CODES.S)
    fun addGeofence(reminder: Reminder) {

        val geofence = Geofence.Builder()
            .setRequestId(reminder.title)
            .setCircularRegion(reminder.location_x.toDouble(), reminder.location_y.toDouble(), 500F)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setLoiteringDelay(60000)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        .putExtra("reminder_id", reminder.reminderId.toString())
        .putExtra("reminder_title", reminder.title)
        .putExtra("reminder_text", reminder.text)
        .putExtra("reminder_location_x", reminder.location_x)
        .putExtra("reminder_location_y", reminder.location_y)

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ),
                    123
                )
                Log.d("Geofence", "permission")
            } else {
                geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                    .addOnSuccessListener {
                        Log.d("Geofence", "Geofence added")
                    }
                    .addOnFailureListener {
                        Log.d("Geofence", "Geofence failed to add")
                    }
            }
        } else {
            geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener {
                    Log.d("Geofence", "Geofence added")
                }
                .addOnFailureListener {
                    Log.d("Geofence", "Geofence failed to add")
                }
        }
    }



    fun removeGeofence(id: String) {
        geofencingClient.removeGeofences(listOf(id))
            .addOnSuccessListener {
                Log.d("Geofence", "Geofence removed")
            }
            .addOnFailureListener {
                Log.d("Geofence", "Geofence failed to remove")
            }
    }

}