package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerList
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.io.use


/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(2)
    private val playerURL = "${baseUrl}players/"

    /**
     * For single requests only
     * @param [id] player id
     * @param [forceRequest]  forces to make a request to API
     * @return [PlayerData]
     */
    @Suppress("unused")
    suspend fun fetchById(id: Int, forceRequest: Boolean = false) : PlayerData {
       val urlForID = "${playerURL}$id"
       if(forceRequest) return JSONResponse(urlForID)
       val data = cache[id]
       return data ?: JSONResponse<PlayerData>(urlForID).also { cache[id] = it }
    }

    /**
     * This method is only for fetching a [List] of 100 [PlayerData] by [name]. Cannot use for concurrent or many api calls; It will result in an error.
     *
     * @param [name] [String] search string
     *
     * @param [forceRequest]  forces to make a request to API
     * @return [List<PlayerData>]
     */
    @Suppress("unused")
    suspend fun fetchByName(name: String, forceRequest: Boolean = false) : List<PlayerData> = coroutineScope {

        val searchNameURL = "${playerURL}?search=$name&per_page=100"
        if(forceRequest) return@coroutineScope JSONResponse<PlayerList>(searchNameURL, true).data
        val ( data ) = JSONResponse<PlayerList>(searchNameURL, true)

        val listOfPlayers = mutableListOf<PlayerData>()

        /**
         * If the cache has player already, adds to List
         * else adds json data and adds cache data
         */

        data.forEach {
            if( cache has it.id ) {
                cache[it.id]?.let { player ->
                    listOfPlayers.add(player)
                }
            } else {
                listOfPlayers.add(it)
                cache[it.id] = it
            }

        }
        listOfPlayers
    }

    /**
     * Turns [extractBody] into JSON with serialization
     * @param [forList] [Boolean] handles both List and Non List version of API responses
     * @return [T] - usually the serializable version of this Class
     */
    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> JSONResponse(url: String, forList: Boolean): T = coroutineScope {
        withContext(Dispatchers.Default) {
            if(!forList)
                Json
                    .decodeFromString<PlayerData>(
                        (Dispatchers.Default) { extractBody(url)}) as T
            else
                Json
                    .decodeFromString<PlayerList>((Dispatchers.Default) { extractBody(url) }) as T

        }

    }



}

















