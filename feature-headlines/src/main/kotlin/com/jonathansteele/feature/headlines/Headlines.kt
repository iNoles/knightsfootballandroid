package com.jonathansteele.feature.headlines

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Immutable
data class Headlines(
    val data: List<Data>
)

@JsonClass(generateAdapter = true)
data class Data(
    val story_created: String?,
    val story_headline: String?,
    val story_path: String?,
    val story_image: String?
)