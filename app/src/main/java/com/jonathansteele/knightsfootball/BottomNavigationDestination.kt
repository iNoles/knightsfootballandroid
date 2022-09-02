package com.jonathansteele.knightsfootball

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import com.jonathansteele.feature.headlines.R as headlinesR
import com.jonathansteele.feature.rosters.R as rostersR
import com.jonathansteele.feature.schedules.R as schedulesR

enum class BottomNavigationDestination {
    Home,
    Schedules,
    Rosters
}

val BottomNavigationDestination.tabTitleId
    get() = when (this) {
        BottomNavigationDestination.Home -> headlinesR.string.headlines
        BottomNavigationDestination.Schedules -> schedulesR.string.schedules
        BottomNavigationDestination.Rosters -> rostersR.string.rosters
    }

val BottomNavigationDestination.unselectedTabIcon
    get() = when (this) {
        BottomNavigationDestination.Home -> Icons.Outlined.Home
        BottomNavigationDestination.Schedules -> Icons.Outlined.Event
        BottomNavigationDestination.Rosters -> Icons.Outlined.Star
    }

val BottomNavigationDestination.selectedTabIcon
    get() = when (this) {
        BottomNavigationDestination.Home -> Icons.Filled.Home
        BottomNavigationDestination.Schedules -> Icons.Filled.Event
        BottomNavigationDestination.Rosters -> Icons.Filled.Star
    }

