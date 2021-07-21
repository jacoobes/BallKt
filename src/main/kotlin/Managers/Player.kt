package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerList
import io.ktor.client.statement.*
import kotlinx.coroutines.*

import kotlin.Error


/**
 * Interacting with Player endpoint
 * */
class Player  : BallManager() {
    private var requestedPage : Int = 1

    /**
     * cacheURL makes bulk requests to API to cache into [cache]
     */
    private val cacheUrl: String = "${super.baseUrl}players/?per_page=100&page=${requestedPage}"
    val cache
        get() = runBlocking {  makeCache() }


    private suspend fun makeCache() {
        TODO()

    }







}











