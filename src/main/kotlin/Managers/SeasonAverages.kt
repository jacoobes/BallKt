package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.SeasonAvgData
import java.time.Year

class SeasonAverages: BallManager() {
    internal val cache = BallCache<Int, SeasonAvgData>(50)
    private val seasonAvgEndpoint = "${client.basePath}/season_averages"

    private fun constructQuery(season: Int = Year.now().value, vararg playerIds :Int ) : String {
        return buildString {
            append(seasonAvgEndpoint)
            append("?season=$season")
            for (id in playerIds ) {
                append("&player_ids[]=$id")
            }
        }
    }

    /**
     * Searches by one player id.
     * season default is current year
     */
    fun fetchById(playerId: Int, season: Int = Year.now().value) : SeasonAvgData {
        if(cache hasKey playerId) return cache[playerId]
        constructQuery(
            season = season,
            playerId
        )
            .httpGet()
            .responseObject(SeasonAvgData.ListDeserialize()) { result ->

                val res = result.get().data[0]
                when(result) {
                    is Result.Failure -> throw result.getException()
                    is Result.Success -> cache[res.player_id] = res
                }
            }.join()
            return cache[playerId]

    }

    


}