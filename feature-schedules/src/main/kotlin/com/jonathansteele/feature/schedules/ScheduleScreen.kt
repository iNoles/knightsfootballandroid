package com.jonathansteele.feature.schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
fun ScheduleListItem(
    schedule: Schedule,
    modifier: Modifier = Modifier
) {
    ListItem(
        leadingContent = { OpponentIcon(url = schedule.opponentLogo, modifier.size(64.dp)) },
        headlineText = { Text(text = schedule.opponent!!) },
        supportingText = { Text(text = schedule.location!!) },
        trailingContent = {
            Text(text = schedule.startDateInstant?.let {
                dateFormatted(publishDate = it)
            } ?: schedule.startDateString!!
            )
        }
    )
}

@Composable
private fun OpponentIcon(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun dateFormatted(publishDate: Instant): String {
    var zoneId by remember { mutableStateOf(ZoneId.systemDefault()) }

    val context = LocalContext.current

    DisposableEffect(context) {
        val receiver = TimeZoneBroadcastReceiver(
            onTimeZoneChanged = { zoneId = ZoneId.systemDefault() }
        )
        receiver.register(context)
        onDispose {
            receiver.unregister(context)
        }
    }

    return DateTimeFormatter.ofPattern("MMM dd / h:mm a")
        .withZone(zoneId).format(publishDate.toJavaInstant())
}

@Preview(showBackground = true)
@Composable
fun ScheduleTabPreview() {
    val schedule = Schedule(
        opponent = "USF",
        location = "Bounce House",
        startDateString = "Today",
        startDateInstant = Clock.System.now(),
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
