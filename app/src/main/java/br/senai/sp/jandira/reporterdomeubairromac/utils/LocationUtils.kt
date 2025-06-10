package br.senai.sp.jandira.reporterdomeubairromac.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationRequest
import android.os.Looper
import android.renderscript.RenderScript
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@SuppressLint("MissingPermission")
fun getUserLocation(context: Context, onLocationResult: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest
        .Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10_000L
    ).setMinUpdateIntervalMillis(5_000L)
        .setMaxUpdateDelayMillis(15_000L)
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            onLocationResult(locationResult.lastLocation)
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}

