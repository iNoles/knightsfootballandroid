package com.jonathansteele.feature.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    headlinesFetcher: HeadlinesFetcher
) :
    ViewModel() {

    // Observe headlines information
    val headlines = flow {
        emit(headlinesFetcher.fetchHeadlines())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}