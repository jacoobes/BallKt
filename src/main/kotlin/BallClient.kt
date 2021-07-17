package dev.seren

import dev.seren.Managers.Game
import dev.seren.Managers.Player

/*
* Base interaction class for interactions with the API
* */
class BallClient {
     val players: Player = Player()
     val games : Game = Game()

}