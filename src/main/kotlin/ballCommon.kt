package dev.seren.common.test


import dev.seren.BallClient

 fun main() {
    val client = BallClient()

    println( client.seasonAverages.fetchMultiple(intArrayOf(237, 100), 2011)  )

}

