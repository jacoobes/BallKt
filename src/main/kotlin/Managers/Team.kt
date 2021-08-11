package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.team.TeamData

@Suppress("unused")
class Team : BallManager() {
    private val teamEndpoint = "${client.basePath}/teams"

    internal val cache = BallCache<Int, TeamData>(60)

    fun fetchByID(id: Int) : TeamData? {
        if(cache hasKey id) return cache[id]
        "$teamEndpoint/$id"
            .httpGet()
            .responseObject(TeamData.Deserializer()) { _, _, result ->
                when(result)  {
                    is Result.Success -> {
                        val team = result.get()
                        cache[team.id] = team
                    }
                    is Result.Failure -> {
                        throw result.getException()
                    }
                }
            }.join()
        return if(cache hasKey id) cache[id] else null
    }

}