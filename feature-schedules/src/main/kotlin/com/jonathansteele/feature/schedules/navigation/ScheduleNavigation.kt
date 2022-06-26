package com.jonathansteele.feature.schedules.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jonathansteele.feature.schedules.SchedulesScreen
import com.jonathansteele.nolesfootball.core.navigation.SportsNavigationDestination

object ScheduleDestination : SportsNavigationDestination {
    override val route: String = "schedule_route"

    override val destination: String = "schedule_destination"
}

fun NavGraphBuilder.schedulesGraph() {
    composable(ScheduleDestination.route) {
        SchedulesScreen()
    }
}