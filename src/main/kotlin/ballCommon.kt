package dev.seren.common.test


import dev.seren.BallClient
import dev.seren.Managers.Player
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

suspend fun main()  {
    val client = BallClient()

    client.players.fetchManyByIDs(1..10)
   val list = client.players.cache.findAll {
        it.first_name.startsWith("La")
    }

}



