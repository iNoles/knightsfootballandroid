package com.jonathansteele.feature.rosters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme

@Composable
fun RostersScreen(
    modifier: Modifier = Modifier,
    viewModel: RostersViewModel = hiltViewModel()
) {
    val rostersState = viewModel.rosters.collectAsState()
    Column(modifier = modifier) {
        SportsTopAppBar(titleRes = R.string.rosters)
        LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
            items(rostersState.value) {
                PlayersListItem(players = it)
            }
        }
    }
}

@Composable
fun PlayersListItem(
    players: Players,
    itemSeparation: Dp = 16.dp
) {
    Column(
        modifier = Modifier.padding(vertical = itemSeparation)
    ) {
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

@Composable
fun CoachesListItem(
    coaches: Coaches,
    itemSeparation: Dp = 16.dp
) {
    Column(
        modifier = Modifier.padding(vertical = itemSeparation)
    ) {
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
fun RostersTabPreview() {
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
