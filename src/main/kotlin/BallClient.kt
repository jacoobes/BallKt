package dev.seren

import dev.seren.Managers.*

/**
*  Base interaction class for interactions with the API.
* - Follows ball dont lie's API rate limiting standards
* - too many requests will rate limit you and crash.
* */
 class BallClient {

     val players = Player()
     val games  = Game()
     val teams = Team()
     val seasonAverages = SeasonAverages()
    /**
     * prints all caches
     */
    fun allCaches() {
        println("Players: ${players.cache}")
        println("Games ${games.cache}")
        println("Teams : ${teams.cache}")
    }

}