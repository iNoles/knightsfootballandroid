package com.jonathansteele.knightsfootball

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {
    @Parcelize
    data object Headlines : Screen()

    @Parcelize
    data object Schedules : Screen()

    @Parcelize
    data object Rosters : Screen()
}
