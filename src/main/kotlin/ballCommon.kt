package dev.seren.common.test


import dev.seren.BallClient
import dev.seren.Managers.Player
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

suspend fun main()  {
    val client = BallClient()
    println(client.players.fetchByName("anthony davis"))
    println(client.players.fetchByName("a"))
    client.players.cache.dump()

}



