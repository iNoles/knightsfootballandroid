package com.jonathansteele.knightsfootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jonathansteele.core.ui.SportsGradientBackground
import com.jonathansteele.core.ui.SportsNavigationBar
import com.jonathansteele.core.ui.SportsNavigationBarItem
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.GradientColors
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import com.jonathansteele.core.ui.theme.LocalGradientColors
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.moveToTop
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController

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
        startDestination = BottomNavigationDestination.Home
    )

    // custom back handler implementation
    BottomNavigationBackHandler(navController)

    val lastDestination = navController.backstack.entries.last().destination
    val shouldShowGradientBackground = lastDestination == BottomNavigationDestination.Home

    KnightsFootballTheme {
        SportsGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    SportsBottomBar(navController)
                }
            ) { padding ->
                Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                    SportsTopAppBar(titleRes = lastDestination.tabTitleId)
                    SportsNavHost(navController = navController)
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
        if (lastEntry.destination == BottomNavigationDestination.Home) {
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
