package com.jonathansteele.knightsfootball

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.jonathansteele.feature.headlines.R.string.headlines
import com.jonathansteele.feature.headlines.navigation.HeadlinesDestination
import com.jonathansteele.feature.rosters.R.string.rosters
import com.jonathansteele.feature.rosters.navigation.RostersDestination
import com.jonathansteele.feature.schedules.R.string.schedules
import com.jonathansteele.feature.schedules.navigation.ScheduleDestination

/**
 * Routes for the different top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */

/**
 * Models the navigation top level actions in the app.
 */
class SportsTopLevelNavigation (private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = HeadlinesDestination.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = headlines
    ),
    TopLevelDestination(
        route = ScheduleDestination.route,
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        iconTextId = schedules
    ),
    TopLevelDestination(
        route = RostersDestination.route,
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        iconTextId = rosters
    )
)