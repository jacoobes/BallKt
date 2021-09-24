package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.SeasonAvgData
import dev.seren.serializables.SeasonAvgDataList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error
import java.time.Year
@Suppress("unused")
class SeasonAverages : BallManager() {
    internal val cache = BallCache<Int, SeasonAvgData>(50)

    private fun constructQuery(season: Int = Year.now().value, vararg playerIds: Int): String {
        return buildString {
            append("$basePath/season_averages")
            append("?season=$season")
            for (id in playerIds) {
                append("&player_ids[]=$id")
            }
        }
    }

    /**
     * Searches by one player id.
     * season default is current year
     */
    fun fetchById(playerId: Int, season: Int = Year.now().value): SeasonAvgData? {
        if (cache hasKey playerId) return cache[playerId]

      return fetch("$basePath/season_averages?season=$season&player_ids[]=$playerId") {
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