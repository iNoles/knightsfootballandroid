package com.jonathansteele.feature.headlines.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jonathansteele.feature.headlines.HeadlinesScreen
import com.jonathansteele.nolesfootball.core.navigation.SportsNavigationDestination

object HeadlinesDestination : SportsNavigationDestination {
    override val route: String = "headlines_route"

    override val destination: String = "headlines_destination"
}

fun NavGraphBuilder.headlinesGraph() {
    composable(HeadlinesDestination.route) {
        HeadlinesScreen()
    }
}