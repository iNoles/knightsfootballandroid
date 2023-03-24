package com.jonathansteele.knightsfootball

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.jonathansteele.feature.headlines.HeadlinesScreen
import com.jonathansteele.feature.rosters.RostersScreen
import com.jonathansteele.feature.schedules.SchedulesScreen
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavController

/**
 * Top-level navigation graph.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SportsNavHost(
    navController: NavController<BottomNavigationDestination>
) {
    AnimatedNavHost(controller = navController) {
        when (it) {
            BottomNavigationDestination.Home -> HeadlinesScreen()
            BottomNavigationDestination.Schedules -> SchedulesScreen()
            BottomNavigationDestination.Rosters -> RostersScreen()
        }
    }
}