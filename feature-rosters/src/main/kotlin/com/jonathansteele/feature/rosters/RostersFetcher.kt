/*
 * Copyright 2021 Jonathan Steele
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.jonathansteele.feature.rosters

import com.jonathansteele.core.network.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * A class which fetches UCF Knights Players from Jsoup.
 *
 * @param okHttpClient [OkHttpClient] to use for network requests
 */
class RostersFetcher @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private suspend fun fetchResources(): Response {
        // Create request for remote resource.
        val request = Request.Builder()
            .url("https://ucfknights.com/sports/football/roster")
            .build()
        val response = okHttpClient.newCall(request).await()

        // If the network request wasn't successful, throw an exception
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response
    }

    suspend fun fetchPlayers(): List<Players> {
        val response = fetchResources()
        return withContext(Dispatchers.IO) {
            response.body.use {
                parsePlayersDOM(it.string())
            }
        }
    }

    suspend fun fetchCoaches(): List<Coaches> {
        val response = fetchResources()
        return withContext(Dispatchers.IO) {
            response.body.use {
                parseCoachesDOM(it.string())
            }
        }
    }
}
