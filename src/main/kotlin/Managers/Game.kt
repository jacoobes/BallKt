package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.team.TeamData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)

    fun fetchById(id: Int) : GameData {
        if(cache hasKey id) return cache[id]
        "${basePath}/games/$id"
            .httpGet()
            .responseObject(GameData.Deserializer()) { result ->
                when(result) {
                    is Result.Success -> cache[id] = result.get()
                    is Result.Failure -> throw result.getException()
                }
            }.join()
        return cache[id]
    }


}