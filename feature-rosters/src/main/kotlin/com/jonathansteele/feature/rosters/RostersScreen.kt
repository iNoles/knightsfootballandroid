package com.jonathansteele.feature.rosters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
        HorizontalPager(
            state = pagerState,
            count = tabState.titles.size,
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(top = 12.dp),
        ) { index ->
            when(index) {
                0 -> {
                    val rostersState by viewModel.players.collectAsState()
                    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
                        items(rostersState) {
                            PlayersListItem(players = it)
                        }
                    }
                }
                1 -> {
                    val rostersState by viewModel.coaches.collectAsState()
                    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
                        items(rostersState) {
                            CoachesListItem(coaches = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayersListItem(
    players: Players,
    modifier: Modifier = Modifier,
    itemSeparation: Dp = 16.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = itemSeparation)
        ) {
            Text(
                text = players.number!!,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = players.name!!,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = players.position!!,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun CoachesListItem(coaches: Coaches) {
    Column {
        Text(
            text = coaches.name!!,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = coaches.position!!,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
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
