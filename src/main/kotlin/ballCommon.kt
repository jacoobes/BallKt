package dev.seren.common

import dev.seren.BallClient
import dev.seren.Managers.Player
import kotlinx.coroutines.runBlocking


 fun main() = runBlocking {
    val client = BallClient()
    println(client.players.fetchById(5))

    println(client.players.cache[3])

}



