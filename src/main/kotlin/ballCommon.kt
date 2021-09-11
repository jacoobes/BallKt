package dev.seren.common.test


import dev.seren.BallClient

suspend fun main() {
    val client = BallClient()
    println(client.teams.fetchAllTeams())


}

