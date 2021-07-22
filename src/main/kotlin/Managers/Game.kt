package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.player.PlayerData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    override val baseUrl: String = "${super.baseUrl}games"

    /**
     * Turns [extractBody] into JSON with serialization
     */
    override suspend fun <T> JSONResponse(url: String): T {
        TODO("Not yet implemented")
    }

    internal val cache = BallCache<String, GameData>()



}