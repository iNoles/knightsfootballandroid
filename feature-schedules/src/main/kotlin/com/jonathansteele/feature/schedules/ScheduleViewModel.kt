package com.jonathansteele.feature.schedules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    scheduleFetcher: ScheduleFetcher
) :
    ViewModel() {

    // Observe schedules information
    val schedules = flow {
        emit(scheduleFetcher.fetchSchedule())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}