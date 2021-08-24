package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.team.TeamData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)

    fun fetchById(id: Int) {
        if(cache hasKey id) cache[id]
        "${basePath}/games/$id"
            .httpGet()

    }
}