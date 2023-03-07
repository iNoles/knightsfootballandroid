package com.jonathansteele.feature.headlines

import android.util.Log
import com.github.kittinunf.result.onFailure
import com.github.kittinunf.result.onSuccess
import fuel.Fuel
import fuel.get
import fuel.moshi.toMoshi
import kotlinx.coroutines.flow.flow
import java.io.IOException

fun fetchHeadlines() = flow {
    try {
        Fuel.get("https://ucfknights.com/services/archives.ashx/stories?index=1&page_size=30&sport=football&season=0&search=")
            .toMoshi(Headlines::class.java)
            .onSuccess { headlines ->
                headlines?.data?.let {
                    emit(it)
                }
            }.onFailure { ioException ->
                ioException.localizedMessage?.let {
                    Log.e("HeadlinesFetcher", it)
                }
            }
    } catch (ioe: IOException) {
        ioe.localizedMessage?.let { Log.e("HeadlinesFetcher", it) }
    }
}
