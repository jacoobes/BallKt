package dev.seren.common


import dev.seren.BallClient
import dev.seren.Managers.Player
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


 suspend fun main() = coroutineScope {
    val client = BallClient()

    for (i in 1..10) {
        client.players.fetchById(i)
    }

    println(client.players.cache)



}



