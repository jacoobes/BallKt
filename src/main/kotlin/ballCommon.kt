package dev.seren.common

import dev.seren.BallClient
import dev.seren.Managers.Player
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


 suspend fun main() = coroutineScope {
    val client = BallClient()


    println(client.players.fetchByName("a"))


}



