package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerList
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString

import kotlin.Error
import kotlin.properties.Delegates


/**
 * Interacting with Player endpoint
 * */
class Player : BallManager() {
    private var requestedPage : Int = 1

    /**
     * cacheURL makes bulk requests to API to cache into [cache]
     */
    private val cacheUrl: String = "${super.baseUrl}players/?per_page=100&page=${requestedPage}"

     val cache by lazy {

         /**
          * An example of serializing the data from a url
          * lazy init meaning this is not computed until it is accessed
          *
          */

      val (data ) = kotlinx.serialization.json.Json.decodeFromString<PlayerList>( runBlocking {   extractBody<PlayerList>(cacheUrl) })
      data
    }








}











