package dev.seren.Managers

import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.team.TeamData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Suppress("unused")
class Team : BallManager() {
    private val teamEndpoint = "${client.basePath}/teams"
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
        "$teamEndpoint/$id"
            .httpGet()
            .responseObject(TeamData.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val team = result.get()
                        cache[team.id] = team
                    }
                    is Result.Failure -> {
                        throw result.getException()
                    }
                }
            }.join()
        return if (cache hasKey id) cache[id] else null
    }

    suspend fun fetchAllTeams(): List<TeamData> {
        val listOfAllTeams = teamEndpoint
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