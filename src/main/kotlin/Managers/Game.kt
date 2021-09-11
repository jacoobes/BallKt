package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.team.TeamData
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
/**
 * Interacting with Game endpoint
 * */
@Suppress("unused")
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)

    /**
     * Method to confirm date is in a correct YYYY-MM-DD
     */
    fun isValidDate(date : String) : Boolean = date.matches("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])".toRegex())

    fun fetchById(id: Int) : GameData? {
      if(cache hasKey id) return cache[id]

      return fetch("${basePath}/games/$id") {
            val data = gson.fromJson(this, GameData::class.java)
            cache[id] = data

            return@fetch data
        }

    }


}