package dev.seren.Managers

import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.SeasonAvgData
import dev.seren.serializables.SeasonAvgDataList
import java.time.Year

@Suppress("unused")
class SeasonAverages : BallManager() {
    internal val cache = BallCache<Int, SeasonAvgData>(50)

    /**
     * Searches by one player id.
     * season default is current year
     */
    fun fetchById(playerId: Int, season: Int = Year.now().value): SeasonAvgData? {
        return if (cache hasKey playerId) cache[playerId]
        else fetch("$basePath/season_averages?season=$season&player_ids[]=$playerId") {
            val type = object : TypeToken<SeasonAvgDataList>() {}.type
            val data = gson.fromJson<SeasonAvgDataList>(this, type).data[0]
            cache[playerId] = data

            return@fetch data
        }

    }

    /**
     * Always fetches from API. Caches all results
     */
     fun fetchMultiple(ids: IntArray, season: Int = Year.now().value) : List<SeasonAvgData> {

     return fetch(
            constructQuery(
                season = season,
                playerIds = ids
            )
        ) {
            val type = object : TypeToken<SeasonAvgDataList>(){}.type
            val ( data ) = gson.fromJson<SeasonAvgDataList>(this, type)
                .also { (data) ->
                    data.forEach { sznAvg ->
                        cache[sznAvg.player_id] = sznAvg
                    }
                }

            return@fetch data
        } ?: emptyList()




    }


}