package com.jonathansteele.feature.schedules

import kotlinx.datetime.Instant

data class Schedule(
    val opponent: String?,
    val location: String?,
    val startDateString: String?,
    val startDateInstant: Instant?,
    val opponentLogo: String?
)