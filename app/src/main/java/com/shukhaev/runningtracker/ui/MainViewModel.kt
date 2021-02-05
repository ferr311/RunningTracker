package com.shukhaev.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.shukhaev.runningtracker.repository.Repository


class MainViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {
}