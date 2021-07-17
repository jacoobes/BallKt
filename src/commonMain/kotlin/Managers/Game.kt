package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.player.PlayerData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    override val baseUrl: String = "${super.baseUrl}games"
    internal val cache = BallCache<String, GameData>()


}