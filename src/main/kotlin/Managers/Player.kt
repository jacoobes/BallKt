package dev.seren.Managers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerDataList
import io.ktor.utils.io.core.*

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

        return fetch("$basePath/players?search=$name&per_page=$max") {
            val type = object : TypeToken<PlayerDataList>() {}.type

            val ( data ) = Gson().fromJson<PlayerDataList>(this, type)

            return@fetch data.onEach { player -> cache[player.id] = player }
        } ?: emptyList()
    }


}













