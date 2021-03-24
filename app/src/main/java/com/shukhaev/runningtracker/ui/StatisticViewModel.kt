package com.shukhaev.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.shukhaev.runningtracker.repository.Repository


class StatisticViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    val totalTimeRun = repository.getTotalTimeInMillis()
    val totalDistance = repository.getTotalDistance()
    val totalCaloriesBurned = repository.getTotalCaloriesBurned()
    val totalAvgSpeed = repository.getTotalSpeed()

    val runsSortedByDate = repository.getAllRunsSortedByDate()
}