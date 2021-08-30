package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.team.TeamData
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)

    fun fetchById(id: Int) : GameData? {
        if(cache hasKey id) return cache[id]
        fetch("${basePath}/games/$id") {
            val data = Gson().fromJson(this, GameData::class.java)
            cache[id] = data
        }
        return if(cache hasKey id) cache[id] else null
    }

    /**
     * Method to confirm date is in a correct YYYY-MM-DD
     */
    fun isValidDate(date : String) : Boolean {
        val regex = Regex("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
        return date.matches(regex)
    }

}