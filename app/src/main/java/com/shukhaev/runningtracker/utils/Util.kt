package com.shukhaev.runningtracker.utils

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit

object Util {
    const val RUNNING_DATABASE_NAME = "running_db"
    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "tracking"
    const val NOTIFICATION_ID = 1

    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f
    const val MAP_ZOOM = 15f

    const val TIMER_UPDATE_DELAY = 50L

    fun hasLocationPermission(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {

        var milliseconds = ms
        val hours = TimeUnit.MICROSECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis) {
            return "${if (hours < 10) "0" else ""}$hours:" +
                    "${if (minutes < 10) "0" else ""}$minutes:" +
                    "${if (seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds:" +
                "${if (milliseconds < 10) "0" else ""}$milliseconds"
    }
}