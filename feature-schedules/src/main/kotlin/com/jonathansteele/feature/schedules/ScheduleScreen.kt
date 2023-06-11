package com.jonathansteele.feature.schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme

@Composable
fun SchedulesScreen() {
    val scheduleState = fetchSchedule().collectAsState(initial = emptyList())
    LazyColumn {
        items(scheduleState.value) {
            ScheduleListItem(schedule = it)
        }
    }
}

@Composable
fun ScheduleListItem(
    schedule: Schedule,
    modifier: Modifier = Modifier
) {
    Card(modifier.padding(16.dp)) {
        ListItem(
            leadingContent = { OpponentIcon(url = schedule.opponentLogo, modifier.size(64.dp)) },
            headlineContent = { Text(text = schedule.opponent!!) },
            supportingContent = { Text(text = schedule.location!!) },
            trailingContent = { Text(text = schedule.startDate!!) }
        )
    }
}

@Composable
private fun OpponentIcon(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScheduleTabPreview() {
    val schedule = Schedule(
        opponent = "USF",
        location = "Bounce House",
        startDate = "Today",
        opponentLogo = "https://ucfknights.com/images/logos/South_Florida.png"
    )
    KnightsFootballTheme {
        SportsBackground {
            Column {
                SportsTopAppBar(titleRes = R.string.schedules)
                ScheduleListItem(schedule = schedule)
            }
        }
    }
}
