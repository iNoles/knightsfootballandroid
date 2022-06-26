package com.jonathansteele.feature.schedules

import com.jonathansteele.core.network.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

/**
 * A class which fetches Seminoles RSS feeds.
 */
class ScheduleFetcher @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    suspend fun fetchSchedule(): List<Schedule> {
        val request = Request.Builder()
            .url("https://ucfknights.com/calendar.ashx/calendar.rss?sport_id=2&=cl4j8ot2n00013g9rx5bxrlh1")
            .build()
        val response = okHttpClient.newCall(request).await()

        // If the network request wasn't successful, throw an exception
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return withContext(Dispatchers.IO) {
            response.body.use {
                parseSchedulesXML(it.byteStream())
            }
        }
    }
}