package com.jonathansteele.feature.schedules

import android.util.Xml
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.StringReader
import java.time.format.DateTimeFormatter

// We don't use namespaces
private val ns: String? = null

@Throws(XmlPullParserException::class, IOException::class)
fun parseSchedulesXML(string: String): List<Schedule> {
    StringReader(string).use {
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(it)
        parser.nextTag()
        parser.nextTag()
        return readChannel(parser)
    }
}

@Throws(XmlPullParserException::class, IOException::class)
private fun readChannel(parser: XmlPullParser): List<Schedule> {
    val entries = mutableListOf<Schedule>()

    parser.require(XmlPullParser.START_TAG, ns, "channel")
    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        // Starts by looking for the item tag
        if (parser.name == "item") {
            entries.add(readItem(parser))
        } else {
            skip(parser)
        }
    }
    return entries
}

// Parses the contents of an item. If it encounters a title, published, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
@Throws(XmlPullParserException::class, IOException::class)
private fun readItem(parser: XmlPullParser): Schedule {
    parser.require(XmlPullParser.START_TAG, ns, "item")
    var opponent: String? = null
    var location: String? = null
    var startDate: String? = null
    var opponentLogo: String? = null
    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        when (parser.name) {
            "s:opponent" -> opponent = readOpponent(parser)
            "ev:location" -> location = readLocation(parser)
            "ev:startdate" -> startDate = readStartDate(parser)
            "s:opponentlogo" -> opponentLogo = readOpponentLogo(parser)
            else -> skip(parser)
        }
    }

    return Schedule(opponent, location, startDate, opponentLogo)
}

// Processes opponent tags in the feed.
@Throws(XmlPullParserException::class, IOException::class)
private fun readOpponent(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "s:opponent")
    val opponent = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "s:opponent")
    return opponent
}

// Processes location tags in the feed.
@Throws(XmlPullParserException::class, IOException::class)
private fun readLocation(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "ev:location")
    val location = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "ev:location")
    return location
}

// Processes startdate tags in the feed.
@Throws(XmlPullParserException::class, IOException::class)
private fun readStartDate(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "ev:startdate")
    val utcTime = readText(parser)
    try {
        val formatter = DateTimeFormatter.ofPattern("MMM dd / h:mm a")
        val instant = utcTime.toInstant().toLocalDateTime(TimeZone.currentSystemDefault())
        return formatter.format(instant.toJavaLocalDateTime())
    } catch (_: IllegalArgumentException) {
    }
    parser.require(XmlPullParser.END_TAG, ns, "ev:startdate")
    return utcTime
}

// Processes gamepromoname tags in the feed.
@Throws(XmlPullParserException::class, IOException::class)
private fun readOpponentLogo(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "s:opponentlogo")
    val location = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "s:opponentlogo")
    return location
}

// For the tags title and summary, extracts their text values.
@Throws(XmlPullParserException::class, IOException::class)
private fun readText(parser: XmlPullParser): String {
    var result = ""
    if (parser.next() == XmlPullParser.TEXT) {
        result = parser.text
        parser.nextTag()
    }
    return result
}

@Throws(XmlPullParserException::class, IOException::class)
private fun skip(parser: XmlPullParser) {
    if (parser.eventType != XmlPullParser.START_TAG) {
        throw IllegalStateException()
    }
    var depth = 1
    while (depth != 0) {
        when (parser.next()) {
            XmlPullParser.END_TAG -> depth--
            XmlPullParser.START_TAG -> depth++
        }
    }
}