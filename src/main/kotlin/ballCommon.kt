package dev.seren.common


import dev.seren.BallClient
import dev.seren.Managers.Player
import dev.seren.serializables.player.PlayerData
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


 fun main() = runBlocking {
    val client = BallClient()
    client.players.fetchById(10)

    client.players.fetchById(1)

    client.players.cache.dump()



     println()

}



