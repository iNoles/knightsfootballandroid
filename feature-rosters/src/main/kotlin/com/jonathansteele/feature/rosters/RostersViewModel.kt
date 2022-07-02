package com.jonathansteele.feature.rosters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RostersViewModel @Inject constructor(
    rostersFetcher: RostersFetcher
) : ViewModel() {

    private val _tabState = MutableStateFlow(
        RostersTabState(
            titles = listOf(R.string.players, R.string.coaches),
            currentIndex = 0
        )
    )
    val tabState: StateFlow<RostersTabState> = _tabState.asStateFlow()

    // Observe rosters information for players and coaches
    val rosters = flow {
        emit(rostersFetcher.fetchRosters())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Rosters(mutableListOf(), mutableListOf())
    )

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = newIndex)
            }
        }
    }
}

data class RostersTabState(
    val titles: List<Int>,
    val currentIndex: Int
)
