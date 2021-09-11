package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.SeasonAvgDataList
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerDataList
import dev.seren.serializables.team.TeamData
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*

/**
 * Interacting with Player endpoint
 * */
@Suppress("unused")
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(60)


    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @return [PlayerData?]
     */
    fun fetchById(id: Int): PlayerData? {

        if (cache hasKey id) return cache[id]

       return fetch("$basePath/players/$id") {
            val data = Gson().fromJson(this, PlayerData::class.java)
            cache[id] = data

           return@fetch data
        }
    }

    /**
     * @param [name] [String] search string
     * @param [max] [Int] max amount of players displayed
     */
    fun fetchByName(name: String, max: Int = 25): List<PlayerData> {
        val cachedPlayers = mutableListOf<PlayerData>()

        /**
         * If the cache has player already, adds to List
         * else adds json data and adds cache data
         */

      return fetch("$basePath/players?search=$name&per_page=$max") {
            val type = object : TypeToken<PlayerDataList>() {}.type
            val response: PlayerDataList = Gson().fromJson(this, type)

            response.data.forEach {
                if(cache hasKey it.id) {
                    cache[it.id].let { playerData ->
                        if (playerData != null) {
                            cachedPlayers.add(playerData)
                        }
                    }
                } else {
                    cachedPlayers.add(it)
                    cache[it.id] = it
                }
            }
          response.data
        } ?: emptyList()
    }


}













