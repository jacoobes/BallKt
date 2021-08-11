package dev.seren

import dev.seren.Managers.Game
import dev.seren.Managers.Player

/**
*  Base interaction class for interactions with the API.
* - Follows ball dont lie's API rate limiting standards
* - too many requests will rate limit you and crash.
* */
 class BallClient(  ) {

     val players: Player = Player()
     val games : Game = Game()

}