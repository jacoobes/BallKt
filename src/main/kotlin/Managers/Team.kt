package dev.seren.Managers

import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.team.TeamData
import dev.seren.serializables.team.TeamDataList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Suppress("unused")
class Team : BallManager() {
    internal val cache = BallCache<Int, TeamData>(30)

    /**
     * count of current NBA teams
     */
    val teamCount: Int = 30

    /**
     * Fetch by ID. Optimized for fetching single requests
     * @param [id] player id
     * @return [TeamData?]
     */
    fun fetchByID(id: Int): TeamData? {
        if (cache hasKey id) return cache[id]

        return fetch("$basePath/teams/$id") {
            val data = gson.fromJson(this, TeamData::class.java)
            cache[id] = data

            return@fetch data
        }
    }

    /**
     * @return emptyList or data
     */
        fun fetchAllTeams(): List<TeamData> {

        return fetch("$basePath/teams") {
            val type = object : TypeToken<TeamDataList>() {}.type
            val ( data ) = gson.fromJson<TeamDataList>(this, type)
                .also { (data) ->
                    data.forEach { team ->
                        cache[team.id] = team
                    }
                }

           return@fetch data
        } ?: emptyList()


    }

}