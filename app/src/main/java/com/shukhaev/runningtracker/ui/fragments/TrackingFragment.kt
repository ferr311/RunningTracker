package com.shukhaev.runningtracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.shukhaev.runningtracker.R
import com.shukhaev.runningtracker.services.Polyline
import com.shukhaev.runningtracker.services.TrackingService
import com.shukhaev.runningtracker.ui.MainViewModel
import com.shukhaev.runningtracker.utils.Util.ACTION_PAUSE_SERVICE
import com.shukhaev.runningtracker.utils.Util.ACTION_START_OR_RESUME_SERVICE
import com.shukhaev.runningtracker.utils.Util.MAP_ZOOM
import com.shukhaev.runningtracker.utils.Util.POLYLINE_COLOR
import com.shukhaev.runningtracker.utils.Util.POLYLINE_WIDTH
import com.shukhaev.runningtracker.utils.Util.getFormattedStopWatchTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()

    private var map: GoogleMap? = null

    private var isTracking = false
    private var pathpoints = mutableListOf<Polyline>()

    private var currentTimeInMillis = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        mapView?.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTrackingUI(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathpoints = it
            addLatestPolyline()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currentTimeInMillis = it
            val formattedTime = getFormattedStopWatchTime(currentTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTrackingUI(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = "START"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "STOP"
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun moveCameraToUser() {
        if (pathpoints.isNotEmpty() && pathpoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathpoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathpoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathpoints.isNotEmpty() && pathpoints.last().size > 1) {
            val preLastLatLng = pathpoints.last()[pathpoints.last().size - 2]
            val lastLatLng = pathpoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}