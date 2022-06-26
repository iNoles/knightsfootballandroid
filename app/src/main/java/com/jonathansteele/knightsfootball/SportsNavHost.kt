package com.jonathansteele.knightsfootball

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jonathansteele.feature.headlines.navigation.HeadlinesDestination
import com.jonathansteele.feature.headlines.navigation.headlinesGraph
import com.jonathansteele.feature.rosters.navigation.rostersGraph
import com.jonathansteele.feature.schedules.navigation.schedulesGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun SportsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HeadlinesDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        headlinesGraph()
        schedulesGraph()
        rostersGraph()
    }
}
