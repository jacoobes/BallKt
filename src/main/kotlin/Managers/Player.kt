package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {

    val cache = BallCache<Int, PlayerData>()

    /**
     * @param [id] - id of player
     * @param [forceRequest] - Default set to false, forces to make a request to API
     * @return [PlayerData]
     */
    suspend fun fetchById(id: Int, forceRequest: Boolean = false) : PlayerData {
       val urlForID = "${baseUrl}/players/$id"
       if(forceRequest) return JSONResponse(urlForID)
       val data = cache[id]
       return data ?: JSONResponse<PlayerData>(urlForID).also { cache[id] = it }

    }

    /**
     * Turns [extractBody] into JSON with serialization
     * @return [T] - usually the serializable version of this Class
     */
    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> JSONResponse(url: String) : T = coroutineScope {
        withContext(Dispatchers.Default) {
            Json
                .decodeFromString<PlayerData>(
                    (Dispatchers.Default) { extractBody<PlayerData>(url) }) as T
        }

    }




}











