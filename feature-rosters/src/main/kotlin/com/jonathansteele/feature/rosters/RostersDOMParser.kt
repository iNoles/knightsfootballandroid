package com.jonathansteele.feature.rosters

import org.jsoup.Jsoup

fun parseRostersDOM(html: String): Rosters {
    val rosters = Rosters(players = mutableListOf(), coaches = mutableListOf())
    val doc = Jsoup.parse(html)
    val playersTR = doc.select("div.sidearm-roster-grid-template-1 > table > tbody > tr")
    playersTR.forEach {
        rosters.players.add(Players(
            number = it.select("td.roster_jerseynum ").text(),
            name = it.select("td.sidearm-table-player-name").text(),
            position = it.select("td.rp_position_short ").text()
        ))
    }
    val coachesTR = doc.select("div.row.collapse div table tbody tr")
    coachesTR.forEach {
        val td = it.select("td")
        rosters.coaches.add(Coaches(
            td[1].text(),
            td[2].text()
        ))
    }
    return rosters
}