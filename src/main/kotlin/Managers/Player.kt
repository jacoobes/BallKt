package dev.seren.Managers

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(100)
    private val playerEndpoint = "${client.basePath}/players"

    @Suppress("unused")
    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @return [PlayerData?]
     */
     fun fetchById(id: Int) : PlayerData? {

      if(cache hasKey id) return cache[id]
      "$playerEndpoint/$id"
          .httpGet()
          .responseObject(PlayerData.Deserializer()) { _, _, result ->
              when(result)  {
                  is Result.Success -> {
                      val player = result.get()
                      cache[player.id] = player
                  }
                  is Result.Failure -> {
                      throw result.getException()
                  }
          }
      }.join()

        return if(cache hasKey id) cache[id] else null
    }

    suspend fun fetchManyByIDs(ids: Set<Int>) : List<PlayerData> = coroutineScope {
            ids.map { id ->
                async(Dispatchers.IO) {
                    if (cache hasKey id) cache[id]
                    else "$playerEndpoint/$id".httpGet().responseObject(PlayerData.Deserializer()).third.get()
                        .also {
                            cache[id] = it
                        }
                }
            }
        }.awaitAll()



    @Suppress("unused")
    /**
     *
     * @param [name] [String] search string
     * @return [List<PlayerData>]
     */
    fun fetchByName(name: String): List<PlayerData> {
        val cachedPlayers = mutableListOf<PlayerData>()

        /**
         * If the cache has player already, adds to List
         * else adds json data and adds cache data
         */

        "$playerEndpoint/?search=$name"
            .httpGet()
            .responseObject(PlayerData.ListDeserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {

                        result.get().data.forEach {
                            if(cache hasKey it.id){
                                cache[it.id]?.let { player -> cachedPlayers.add(player) }
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













