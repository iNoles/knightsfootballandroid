package com.jonathansteele.feature.schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme

@Composable
fun SchedulesScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val scheduleState = viewModel.schedules.collectAsState()
    Column(modifier = modifier) {
        SportsTopAppBar(titleRes = R.string.schedules)
        LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
            items(scheduleState.value) {
                ScheduleListItem(schedule = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListItem(schedule: Schedule) {
    ListItem(
        headlineText = { Text(text = schedule.opponent!!) },
        supportingText = { Text(text = schedule.location!!) },
        trailingContent = { Text(text = schedule.startDate!!) }
    )
}

@Preview(showBackground = true)
@Composable
fun ScheduleTabPreview() {
    val schedule = Schedule(
        opponent = "USF",
        location = "Bounce House",
        startDate = "Today"
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
