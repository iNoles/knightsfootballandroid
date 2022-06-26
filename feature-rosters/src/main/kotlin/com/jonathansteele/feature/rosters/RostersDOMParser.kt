package com.jonathansteele.feature.rosters

import org.jsoup.Jsoup

fun parsePlayersDOM(html: String): List<Players> {
    val rostersList = mutableListOf<Players>()
    val doc = Jsoup.parse(html)
    val tr = doc.select("div.sidearm-roster-grid-template-1 > table > tbody > tr")
    tr.forEach {
        rostersList.add(
            Players(
            number = it.select("td.roster_jerseynum ").text(),
            name = it.select("td.sidearm-table-player-name").text(),
            position = it.select("td.rp_position_short ").text()
        ))
    }
    return rostersList
}

fun parseCoachesDOM(html: String): List<Coaches> {
    val coachesList = mutableListOf<Coaches>()
    val doc = Jsoup.parse(html)
    val tr = doc.select("div.row.collapse div table tbody tr")
    tr.forEach {
        val td = it.select("td")
        coachesList.add(Coaches(
            td[1].text(),
            td[2].text()
        ))
    }
    return coachesList
}