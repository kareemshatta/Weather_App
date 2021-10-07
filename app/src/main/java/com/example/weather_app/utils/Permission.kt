package com.example.weather_app.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


object Permission {

    interface Callback {
        fun isPermissionAccepted(isAccepted: Boolean?)
    }

    @SuppressLint("InlinedApi")
    fun checkLocationFineAndCoarsePermission(activity: Activity?, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted() ?: false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }
    fun isInFlightMode(appContext: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.AIRPLANE_MODE_ON,
                0
            ) != 0

        } else {
            return Settings.Global.getInt(
                appContext.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
}
