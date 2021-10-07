package com.example.weather_app.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import com.example.weather_app.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


class LocationFetcher(private val activity: Activity) {
    companion object {
        const val requestCheckSettings = 1234
    }

    private var locationCallback: LocationCallback? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationResponseCallback: LocationResponseCallback? = null

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    fun getLastLocation(callbacks: LocationResponseCallback) {
        locationResponseCallback = callbacks
        checkLocationPermission(::requestLastLocation, ::permissionDenied)
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        val task = fusedLocationClient?.lastLocation
        task?.addOnSuccessListener { location: Location? ->
            if (location != null) {
                locationResponseCallback?.onSuccess(
                    LocationResultModel(location.latitude, location.longitude)
                )
            } else {
                onLastLocationFailed()
            }
        }?.addOnFailureListener {
            onLastLocationFailed()
        }
    }

    private fun onLastLocationFailed() {
        // define location callback now
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                locationResponseCallback?.onSuccess(
                    LocationResultModel(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                )
                fusedLocationClient?.removeLocationUpdates(locationCallback)
            }

            @SuppressLint("MissingPermission")
            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                super.onLocationAvailability(locationAvailability)
                if (locationAvailability?.isLocationAvailable == false) {
                    locationResponseCallback?.onFailed(LocationException.LocationNotAvailable)
                    fusedLocationClient?.removeLocationUpdates(locationCallback)
                }
            }
        }
        if (Permission.isInFlightMode(activity)) {
            locationResponseCallback?.onFailed(LocationException.DeviceInFlightMode)
        } else {
            checkLocationPermission(::requestCurrentLocation, ::permissionDenied)
        }
    }

    private fun checkLocationPermission(
        permissionAcceptedAction: () -> Unit,
        permissionDeniedAction: () -> Unit
    ) {
        if (Permission.hasPermissions(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            permissionAcceptedAction()
        } else {
            Permission.checkLocationFineAndCoarsePermission(activity,
                object : Permission.Callback {
                    override fun isPermissionAccepted(isAccepted: Boolean?) {
                        if (isAccepted == true) {
                            permissionAcceptedAction()
                        } else {
                            permissionDeniedAction()
                        }
                    }

                })
        }
    }

    private fun permissionDenied() {
        locationResponseCallback?.onFailed(LocationException.LocationPermissionDenied)
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.permission_denied))
            .setMessage(activity.getString(R.string.we_needs_access_to_your_location_so_that_you_can_pick))
            .setNegativeButton(activity.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(activity.getString(R.string.settings)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:" + activity.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

                }
                activity.startActivity(intent)
                dialog.dismiss()
            }
            .create()
            .show()
    }


    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }


        // check current location settings
        val locationSettingBuilder = LocationSettingsRequest.Builder().addLocationRequest(
            locationRequest
        ).build()
        val task: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(activity)
            .checkLocationSettings(
                locationSettingBuilder
            )

        task.addOnSuccessListener {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                try {
                    exception.startResolutionForResult(activity, requestCheckSettings)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCheckSettings && resultCode == Activity.RESULT_OK) {
            requestCurrentLocation()
        } else {
            val locationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationResponseCallback?.onFailed(LocationException.LocationNotAvailable)
            } else {
                locationResponseCallback?.onFailed(LocationException.LocationOptimizationPermissionNotGranted)
            }
        }
    }
}

data class LocationResultModel(var lat: Double, var long: Double)

interface LocationResponseCallback {
    fun onSuccess(locationResultModel: LocationResultModel)
    fun onFailed(locationException: LocationException)
}

enum class LocationException {
    DeviceInFlightMode,
    LocationPermissionDenied,
    LocationOptimizationPermissionNotGranted,
    LocationNotAvailable,
    UnexpectedError
}

