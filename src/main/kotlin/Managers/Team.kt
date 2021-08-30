package dev.seren.Managers

import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import dev.seren.BallCache
import dev.seren.serializables.team.TeamData
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
        fetch("$basePath/teams/$id") {
            val data = Gson().fromJson(this, TeamData::class.java)

            cache[id] = data
        }
        return if(cache hasKey id) cache[id] else null
    }

    suspend fun fetchAllTeams(): List<TeamData> {
        val listOfAllTeams = "$basePath/teams"
            .httpGet()
            .awaitResponse(TeamData.ListDeserializer()).third.data

        coroutineScope {
            launch(Dispatchers.Main){
                listOfAllTeams.forEach { team ->
                    cache[team.id] = team
                }
            }
        }
        return listOfAllTeams
    }

}