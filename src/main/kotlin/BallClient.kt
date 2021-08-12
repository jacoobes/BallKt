package dev.seren

import dev.seren.Managers.Game
import dev.seren.Managers.Player
import dev.seren.Managers.Team

/**
*  Base interaction class for interactions with the API.
* - Follows ball dont lie's API rate limiting standards
* - too many requests will rate limit you and crash.
* */
 class BallClient {

     val players = Player()
     val games  = Game()
     val teams = Team()

}