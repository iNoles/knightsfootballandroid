package com.jonathansteele.feature.headlines

import com.jonathansteele.core.network.await
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSource
import java.io.IOException
import javax.inject.Inject

class HeadlinesFetcher @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    suspend fun fetchHeadlines() : List<Data>? {
        val request = Request.Builder()
            .url("https://ucfknights.com/services/archives.ashx/stories?index=1&page_size=30&sport=football&season=0&search=")
            .build()
        val response = okHttpClient.newCall(request).await()

        // If the network request wasn't successful, throw an exception
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return withContext(Dispatchers.IO) {
            response.body.use {
                parsingHeadlinesJSON(it.source())
            }
        }
    }

    private fun parsingHeadlinesJSON(bufferedSource: BufferedSource): List<Data>? {
        Moshi.Builder().build().adapter(Headlines::class.java).apply {
            return fromJson(bufferedSource)?.data
        }
    }
}