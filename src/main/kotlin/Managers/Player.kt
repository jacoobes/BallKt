package dev.seren.Managers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.await
import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerDataList
import io.ktor.utils.io.core.*

/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {

    internal val cache = BallCache<Int, PlayerData>(100)


    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @param [forceRequest]  forces to make a request to API
     * @return [PlayerData]
     */
//    @Suppress("unused")
//    suspend fun fetchById(id: Int, forceRequest: Boolean = false) : PlayerData {
//       val urlForID = "${playerURL}$id"
//       if(forceRequest) return JSONResponse(urlForID)
//       val data = cache[id]
//       return data ?: JSONResponse<PlayerData>(urlForID).also { cache[id] = it }
//    }

    /**
     * This method is only for fetching a [List] of 100 [PlayerData] by [name].
     *
     * @param [name] [String] search string
     *
     * @param [forceRequest]  forces to make a request to API
     * @return [List<PlayerData>]
     */
//    @Suppress("unused")
//    suspend fun fetchByName(name: String, forceRequest: Boolean = false) : List<PlayerData> = coroutineScope {
//
//        val searchNameURL = "${playerURL}?search=$name&per_page=100"
//        if(forceRequest) return@coroutineScope JSONResponse<PlayerList>(searchNameURL, true).data
//        val ( data ) = JSONResponse<PlayerList>(searchNameURL, true)
//
//        val listOfPlayers = mutableListOf<PlayerData>()
//
//        /**
//         * If the cache has player already, adds to List
//         * else adds json data and adds cache data
//         */
//
//        data.forEach {
//            if( cache has it.id ) {
//                cache[it.id]?.let { player ->
//                    listOfPlayers.add(player)
//                }
//            } else {
//                listOfPlayers.add(it)
//                cache[it.id] = it
//            }
//
//        }
//        listOfPlayers
//    }
//
//
//    fun playerList(url: String): List<PlayerData> {
//        val listOfPlayers: MutableList<PlayerData> = mutableListOf()
//        extractBody(url)
//            .responseObject(PlayerData.ListDeserializer()) { _, _, result ->
//                val (data, _) = result
//                if (result is Result.Failure) throw result.getException()
//                println(result)
//                data?.forEach {
//                    listOfPlayers.add(it)
//                }
//
//
//            }.join()
//        return listOfPlayers
//
//    }

    fun playerData(url: String): PlayerDataList{
        val (request, response, result) =  url.httpGet().responseObject(PlayerData.ListDeserializer())
        return result.get()
    }


}













