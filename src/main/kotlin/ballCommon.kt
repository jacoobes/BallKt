package dev.seren.common.test


import dev.seren.BallClient

//inline fun <T> T.onlyIf( boolean: T.() -> Boolean  ) : T? = if(boolean()) this else null

 suspend fun main() {
    val client = BallClient()
    println(client.games.fetchByDate("2020-08-10"))
}

