package dev.seren.Managers

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import io.ktor.utils.io.core.*

/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(100)
    private val playerEndpoint = "${client.basePath}/players"
    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @return [PlayerData?]
     */

    @Suppress("unused")
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













