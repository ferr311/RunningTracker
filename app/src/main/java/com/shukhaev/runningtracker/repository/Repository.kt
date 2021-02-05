package com.shukhaev.runningtracker.repository

import com.shukhaev.runningtracker.db.Run
import com.shukhaev.runningtracker.db.RunDAO
import javax.inject.Inject

class Repository @Inject constructor(private val runDAO: RunDAO) {

    suspend fun insertRun(run: Run) = runDAO.insertRun(run)
    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)

    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()
    fun getAllRunsSortedByCalories() = runDAO.getAllRunsSortedByCalories()
    fun getAllRunsSortedByDistance()= runDAO.getAllRunsSortedByDistance()
    fun getAllRunsSortedBySpeed() = runDAO.getAllRunsSortedBySpeed()
    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()

    fun getTotalSpeed() = runDAO.getTotalSpeed()
    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()
    fun getTotalDistance() = runDAO.getTotalDistance()
    fun getTotalTimeInMillis() = runDAO.getTotalTimeInMillis()
}