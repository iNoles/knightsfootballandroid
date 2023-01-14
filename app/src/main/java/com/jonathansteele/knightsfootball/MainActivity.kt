package com.jonathansteele.knightsfootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jonathansteele.core.ui.*
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import com.jonathansteele.feature.headlines.HeadlinesScreen
import com.jonathansteele.feature.rosters.RostersScreen
import com.jonathansteele.feature.schedules.SchedulesScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.olshevski.navigation.reimagined.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController(
        startDestination = BottomNavigationDestination.values()[0],
    )

    // custom back handler implementation
    BottomNavigationBackHandler(navController)

    KnightsFootballTheme {
        SportsBackground {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    SportsBottomBar(navController)
                }
            ) { padding ->
                val modifier = Modifier.padding(padding)
                AnimatedNavHost(controller = navController) {
                    when(it) {
                        BottomNavigationDestination.Home -> HeadlinesScreen(modifier = modifier)
                        BottomNavigationDestination.Schedules -> SchedulesScreen(modifier = modifier)
                        BottomNavigationDestination.Rosters -> RostersScreen(modifier = modifier)
                    }
                }
            }
        }
    }
}

@Composable
private fun SportsBottomBar(navController: NavController<BottomNavigationDestination>) {
    val lastDestination = navController.backstack.entries.last().destination
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        SportsNavigationBar {
            BottomNavigationDestination.values().forEach { destination ->
                val tabTitle = stringResource(destination.tabTitleId)
                SportsNavigationBarItem(
                    label = { Text(tabTitle) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedTabIcon,
                            contentDescription = tabTitle
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedTabIcon,
                            contentDescription = tabTitle
                        )
                    },
                    selected = destination == lastDestination,
                    onClick = {
                        // keep only one instance of a destination in the backstack
                        if (!navController.moveToTop { it == destination }) {
                            // if there is no existing instance, add it
                            navController.navigate(destination)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBackHandler(
    navController: NavController<BottomNavigationDestination>
) {
    BackHandler(enabled = navController.backstack.entries.size > 1) {
        val lastEntry = navController.backstack.entries.last()
        if (lastEntry.destination == BottomNavigationDestination.values()[0]) {
            // The start destination should always be the last to pop. We move it to the start
            // to preserve its saved state and view models.
            navController.moveLastEntryToStart()
        } else {
            navController.pop()
        }
    }
}

private fun NavController<BottomNavigationDestination>.moveLastEntryToStart() {
    setNewBackstack(
        entries = backstack.entries.toMutableList().also {
            val entry = it.removeLast()
            it.add(0, entry)
        },
        action = NavAction.Pop
    )
}

