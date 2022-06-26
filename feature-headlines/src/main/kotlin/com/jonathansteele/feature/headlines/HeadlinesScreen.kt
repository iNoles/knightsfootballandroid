package com.jonathansteele.feature.headlines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jonathansteele.core.ui.SportsBackground
import com.jonathansteele.core.ui.SportsTopAppBar
import com.jonathansteele.core.ui.theme.KnightsFootballTheme

@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    viewModel: HeadlinesViewModel = hiltViewModel()
) {
    val headlinesState = viewModel.headlines.collectAsState()
    Column(modifier = modifier) {
        SportsTopAppBar(titleRes = R.string.headlines)
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(headlinesState.value!!) {
                HeadlinesListItem(data = it)
            }
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
            HeadlinesIcon(url = data.story_image, iconModifier.size(64.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = data.story_headline!!,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = data.story_created!!,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeadlinesTabPreview() {
    val data = Data(
        story_created = "Today",
        story_headline = "UCF Won",
        story_path = "https://ucfknights.com/",
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
