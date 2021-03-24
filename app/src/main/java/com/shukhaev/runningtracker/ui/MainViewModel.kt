package com.shukhaev.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shukhaev.runningtracker.db.Run
import com.shukhaev.runningtracker.repository.Repository
import com.shukhaev.runningtracker.utils.SortTypes
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val runsSortedByDate = repository.getAllRunsSortedByDate()
    private val runsSortedByDistance = repository.getAllRunsSortedByDistance()
    private val runsSortedByCaloriesBurned = repository.getAllRunsSortedByCalories()
    private val runsSortedByTime = repository.getAllRunsSortedByTimeInMillis()
    private val runsSortedBySpeed = repository.getAllRunsSortedBySpeed()

    val runs = MediatorLiveData<List<Run>>()
    var sortType = SortTypes.DATE

    init {
        runs.addSource(runsSortedByDate) { runsList ->
            if (sortType == SortTypes.DATE){
                runsList?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedBySpeed) { runsList ->
            if (sortType == SortTypes.AVG_SPEED){
                runsList?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByCaloriesBurned) { runsList ->
            if (sortType == SortTypes.CALORIES_BURNED){
                runsList?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByDistance) { runsList ->
            if (sortType == SortTypes.DISTANCE){
                runsList?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByTime) { runsList ->
            if (sortType == SortTypes.RUNNING_TIME){
                runsList?.let { runs.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortTypes)= when(sortType){
        SortTypes.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortTypes.RUNNING_TIME -> runsSortedByTime.value?.let { runs.value = it }
        SortTypes.AVG_SPEED -> runsSortedBySpeed.value?.let { runs.value = it }
        SortTypes.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
        SortTypes.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
    }.also { this.sortType = sortType }

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }
}