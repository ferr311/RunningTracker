package com.shukhaev.runningtracker.services

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.shukhaev.runningtracker.utils.Util.ACTION_PAUSE_SERVICE
import com.shukhaev.runningtracker.utils.Util.ACTION_START_OR_RESUME_SERVICE
import com.shukhaev.runningtracker.utils.Util.ACTION_STOP_SERVICE

class TrackingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Log.d("SERVICE_TAG","started or resumed service")
                }
                ACTION_PAUSE_SERVICE -> {
                    Log.d("SERVICE_TAG","paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Log.d("SERVICE_TAG","stopped service")
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

}