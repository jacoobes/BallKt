package dev.seren.common


import dev.seren.BallClient
import dev.seren.Managers.Player
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


 suspend fun main() = coroutineScope {
    val test = BallClient().players.fetchById(10)
    val joe = BallClient().players.fetchById(11)


}



