package dev.seren.Managers

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import dev.seren.BallCache
import dev.seren.Cache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.Deferred

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)



    private fun constructQuery(): String {
        return basePath
    }

}