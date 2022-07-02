package com.jonathansteele.feature.rosters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTab
import com.jonathansteele.core.ui.SportsTabRow
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RostersScreen(
    modifier: Modifier = Modifier,
    viewModel: RostersViewModel = hiltViewModel()
) {
    val tabState by viewModel.tabState.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = modifier) {
        SportsTopAppBar(titleRes = R.string.rosters)
        SportsTabRow(selectedTabIndex = pagerState.currentPage) {
            tabState.titles.forEachIndexed { index, titleId ->
                SportsTab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        viewModel.switchTab(index)
                        scope.launch {
                            pagerState.scrollToPage(index)
                        } },
                    text = { Text(text = stringResource(id = titleId)) }
                )
            }
        }
        val rostersState by viewModel.rosters.collectAsState()
        HorizontalPager(
            state = pagerState,
            count = tabState.titles.size,
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(top = 12.dp),
        ) { index ->
            when(index) {
                0 -> {
                    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
                        items(rostersState.players) {
                            PlayersListItem(players = it)
                        }
                    }
                }
                1 -> {
                    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
                        items(rostersState.coaches) {
                            CoachesListItem(coaches = it)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersListItem(players: Players) {
    ListItem(
        headlineText = { Text(text = players.name!!) },
        supportingText = { Text(text = players.position!!) },
        leadingContent = { Text(text = players.number!!) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoachesListItem(coaches: Coaches) {
    ListItem(
        headlineText = { Text(text = coaches.name!!) },
        supportingText = { Text(text = coaches.position!!) }
    )
}

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
