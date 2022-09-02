package com.jonathansteele.knightsfootball

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {
    @Parcelize
    object Headlines : Screen()

    @Parcelize
    object Schedules : Screen()

    @Parcelize
    object Rosters: Screen()
}
