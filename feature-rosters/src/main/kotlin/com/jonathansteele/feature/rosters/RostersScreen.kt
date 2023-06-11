package com.jonathansteele.feature.rosters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTab
import com.jonathansteele.core.ui.SportsTabRow
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RostersScreen() {
    val pagerTitles = listOf(R.string.players, R.string.coaches)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    SportsTabRow(selectedTabIndex = pagerState.currentPage) {
        pagerTitles.forEachIndexed { index, titleId ->
            SportsTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                text = { Text(text = stringResource(id = titleId)) }
            )
        }
    }
    val rostersState by fetchRosters().collectAsState(initial = null)
    HorizontalPager(
        state = pagerState,
        pageCount = pagerTitles.size,
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(top = 12.dp),
    ) { index ->
        when (index) {
            0 -> {
                LazyColumn {
                    items(rostersState?.players ?: emptyList()) {
                        PlayersListItem(players = it)
                    }
                }
            }

            1 -> {
                LazyColumn {
                    items(rostersState?.coaches ?: emptyList()) {
                        CoachesListItem(coaches = it)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayersListItem(players: Players) {
    ListItem(
        headlineContent = { Text(text = players.name!!) },
        supportingContent = { Text(text = players.position!!) },
        leadingContent = { Text(text = players.number!!) }
    )
}

@Composable
fun CoachesListItem(coaches: Coaches) {
    ListItem(
        headlineContent = { Text(text = coaches.name!!) },
        supportingContent = { Text(text = coaches.position!!) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RostersPlayersPreview() {
    val players = Players(
        "0",
        "Marco Domino",
        "CB"
    )
    KnightsFootballTheme {
        SportsBackground {
            Column {
                SportsTopAppBar(titleRes = R.string.rosters)
                PlayersListItem(players = players)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RostersCoachesPreview() {
    val coaches = Coaches(
        "Gus Malzahn",
        "Head Coach"
    )
    KnightsFootballTheme {
        SportsBackground {
            Column {
                SportsTopAppBar(titleRes = R.string.rosters)
                CoachesListItem(coaches = coaches)
            }
        }
    }
}
