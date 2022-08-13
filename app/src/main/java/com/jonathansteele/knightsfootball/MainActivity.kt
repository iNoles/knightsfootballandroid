package com.jonathansteele.knightsfootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jonathansteele.core.ui.*
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(calculateWindowSizeClass(this))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(windowSizeClass: WindowSizeClass) {
    KnightsFootballTheme {
        val navController = rememberNavController()
        val sportsTopLevelNavigation = remember(navController) {
            SportsTopLevelNavigation(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        SportsBackground {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
                    ) {
                        SportsBottomBar(
                            onNavigateToTopLevelDestination = sportsTopLevelNavigation::navigateTo,
                            currentDestination = currentDestination
                        )
                    }
                }
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact &&
                        windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
                    ) {
                        SportsNavRail(
                            onNavigateToTopLevelDestination = sportsTopLevelNavigation::navigateTo,
                            currentDestination = currentDestination
                        )
                    }
                }
                SportsNavHost(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

@Composable
private fun SportsNavRail(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    SportsNavigationRail(modifier = modifier) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            SportsNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                               },
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}


@Composable
private fun SportsBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        SportsNavigationBar {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                SportsNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KnightsFootballTheme {
        Greeting("Android")
    }
}*/