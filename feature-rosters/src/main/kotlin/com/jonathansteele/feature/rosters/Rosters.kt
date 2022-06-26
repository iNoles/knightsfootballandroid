package com.jonathansteele.feature.rosters

import androidx.compose.runtime.Immutable

@Immutable
data class Players(
    val number: String?,
    val name: String?,
    val position: String?,
)

@Immutable
data class Coaches(
    val name: String?,
    val position: String?
)
