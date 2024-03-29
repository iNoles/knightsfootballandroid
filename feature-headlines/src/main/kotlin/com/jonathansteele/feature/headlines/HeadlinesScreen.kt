package com.jonathansteele.feature.headlines

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme

@Composable
fun HeadlinesScreen() {
    val headlinesState = fetchHeadlines().collectAsState(initial = emptyList())
    LazyColumn {
        items(headlinesState.value) {
            HeadlinesListItem(data = it)
        }
    }
}

@Composable
private fun HeadlinesIcon(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = "https://ucfknights.com$url",
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun HeadlinesListItem(
    data: Data,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    val launchResourceIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse("https://ucfknights.com${data.story_path}"))
    val context = LocalContext.current
    Card(modifier.padding(16.dp)) {
        ListItem(
            headlineContent = { Text(text = data.story_headline!!) },
            supportingContent = { Text(text = data.story_created!!) },
            leadingContent = {
                HeadlinesIcon(url = data.story_image, iconModifier.size(64.dp))
            },
            modifier = modifier.clickable {
                ContextCompat.startActivity(context, launchResourceIntent, null)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HeadlinesTabPreview() {
    val data = Data(
        story_created = "Today",
        story_headline = "UCF Won",
        story_path = "https://ucfknights.com",
        story_image = "/images/2022/6/14/3MG.jpg"
    )
    KnightsFootballTheme {
        SportsBackground {
            Column {
                SportsTopAppBar(titleRes = R.string.headlines)
                HeadlinesListItem(data = data)
            }
        }
    }
}
