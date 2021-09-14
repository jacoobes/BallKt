package dev.seren.common.test


import dev.seren.BallClient

//inline fun <T> T.onlyIf( boolean: T.() -> Boolean  ) : T? = if(boolean()) this else null

 fun main() {
    val client = BallClient()

    client.seasonAverages.fetchById(10)
}

