package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.SeasonAvgData
import java.time.Year

class SeasonAverages: BallManager() {
    internal val cache = BallCache<Int, SeasonAvgData>(50)
    private val seasonAvgEndpoint = "${client.basePath}/season_averages"

    private fun constructQuery(playerIds : Set<Int>, season: Int = Year.now().value ) : String {
        return buildString {
            append(seasonAvgEndpoint)
            append("?season=$season")
            for (id in playerIds ) {
                append("&player_ids[]=$id")
            }
        }
    }


}