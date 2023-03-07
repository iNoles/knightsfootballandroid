package com.jonathansteele.feature.schedules

import fuel.Fuel
import fuel.get
import kotlinx.coroutines.flow.flow

/**
 * A class which fetches Seminoles RSS feeds.
 */
fun fetchSchedule() = flow {
    val body =
        Fuel.get("https://ucfknights.com/calendar.ashx/calendar.rss?sport_id=2&=cl4j8ot2n00013g9rx5bxrlh1").body
    emit(parseSchedulesXML(body))
}