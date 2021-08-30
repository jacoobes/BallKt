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

        fetch("$basePath/season_averages?season=$season&player_ids[]=$playerId") {
            val type = object : TypeToken<SeasonAvgDataList>() {}.type
            val response = Gson().fromJson<SeasonAvgDataList>(this, type).data[0]

            cache[playerId] = response


        }
        return if (cache hasKey playerId) cache[playerId] else null
    }

    /**
     * Always fetches from API. Caches all results
     */
    suspend fun fetchMultiple(ids: IntArray, season: Int = Year.now().value) = coroutineScope {

        val list = withContext(Dispatchers.Default) {
            constructQuery(season = season, playerIds = ids)
                .httpGet()
                .responseObject(SeasonAvgData.ListDeserialize())
                .third.get().data
        }

        launch {
            list.forEach {
                cache[it.player_id] = it
            }
        }

        return@coroutineScope list
    }


}