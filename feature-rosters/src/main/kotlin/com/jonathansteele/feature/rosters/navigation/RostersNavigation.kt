package com.jonathansteele.feature.rosters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jonathansteele.feature.rosters.RostersScreen
import com.jonathansteele.nolesfootball.core.navigation.SportsNavigationDestination

object RostersDestination : SportsNavigationDestination {
    override val route: String = "rosters_route"

    override val destination: String = "rosters_destination"
}

fun NavGraphBuilder.rostersGraph() {
    composable(RostersDestination.route) {
        RostersScreen()
    }
}