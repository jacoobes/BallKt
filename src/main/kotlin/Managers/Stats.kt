package dev.seren.Managers

import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.ListStats
import dev.seren.serializables.StatsData


class Stats : BallManager() {
    internal val cache = BallCache<Int, StatsData>(60)

    fun fetchById(id: Int): StatsData? {
        return if (cache hasKey id) cache[id]
        else fetch("${basePath}/stats?player_ids[]=${id}") {
            val type = object : TypeToken<ListStats>() {}.type
            val data = gson.fromJson<ListStats>(this, type).data.first()
            cache[id] = data

            return@fetch data
        }
    }
}