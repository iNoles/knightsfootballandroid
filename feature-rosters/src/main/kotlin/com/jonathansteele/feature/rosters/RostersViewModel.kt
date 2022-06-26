package com.jonathansteele.feature.rosters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RostersViewModel @Inject constructor(
    rostersFetcher: RostersFetcher
) :
    ViewModel() {

    // Observe rosters information
    val rosters = flow {
        emit(rostersFetcher.fetchPlayers())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}