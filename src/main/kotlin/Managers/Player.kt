package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*

/**
 * Interacting with Player endpoint
 * */
@Suppress("unused")
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(60)
    private val playerEndpoint = "${client.basePath}/players"

    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @return [PlayerData?]
     */
    fun fetchById(id: Int): PlayerData? {

        if (cache hasKey id) return cache[id]
        "$playerEndpoint/$id"
            .httpGet()
            .responseObject(PlayerData.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val player = result.get()
                        cache[player.id] = player
                    }
                    is Result.Failure -> {
                        throw result.getException()
                    }
                }
            }.join()

        return if (cache hasKey id) cache[id] else null
    }

    /**
     * List version of fetching many by ids
     * Works as a cacher and API fetcher. Always makes an API call. Searches cache while making API calls.
     * @throws [FuelError]
     */
    suspend fun fetchManyByIDs(ids: Set<Int>): List<PlayerData> = coroutineScope {

        ids.map { id ->
            async(Dispatchers.IO) {
                if (cache hasKey id) cache[id]
                else "$playerEndpoint/$id".httpGet().responseObject(PlayerData.Deserializer()).third.get()
                    .also {
                        cache[id] = it
                    }
            }
        }.awaitAll()
    }

    /**
     * Range version of fetching many by ids
     * Works as a cacher and API fetcher. Always makes an API call. Searches cache while making API calls.
     * @throws [FuelError]
     * @return List<Deferred<PlayerData>>
     */
    suspend fun fetchManyByIDs(ids: IntRange): List<PlayerData> = coroutineScope {
        ids.map { id ->
            async(Dispatchers.IO) {
                if (cache hasKey id) cache[id]
                else "$playerEndpoint/$id".httpGet().responseObject(PlayerData.Deserializer()).third.get()
                    .also {
                        cache[id] = it
                    }
            }
        }.awaitAll()
    }

            /**
             * @param [name] [String] search string
             * @param [max] [Int] max amount of players displayed
             * @return [List<PlayerData>]
             */
    fun fetchByName(name: String, max: Int): List<PlayerData> {
        val cachedPlayers = mutableListOf<PlayerData>()

        /**
         * If the cache has player already, adds to List
         * else adds json data and adds cache data
         */

        "$playerEndpoint/?search=$name?per_page=$max"
            .httpGet()
            .responseObject(PlayerData.ListDeserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {

                        result.get().data.forEach {
                            if (cache hasKey it.id) {
                                cache[it.id].let { player -> cachedPlayers.add(player) }
                            } else {
                                cachedPlayers.add(it)
                                cache[it.id] = it
                            }
                        }

                    }
                    is Result.Failure -> throw result.getException()

                }
            }.join()

        return cachedPlayers
    }


}













