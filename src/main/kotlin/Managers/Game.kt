package dev.seren.Managers

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    fun fetchById(id: Int) : GameData {
        if(cache hasKey id) return cache[id]
        "${basePath}/games/$id"
            .httpGet()
            .responseObject(GameData.Deserializer()) { result ->
                when(result) {
                    is Result.Success -> cache[id] = result.get()
                    is Result.Failure -> throw result.getException()
                }
            }.join()
        return cache[id]
    }

    /**
     * Method to confirm date is in a correct YYYY-MM-DD
     */
    fun isValidDate(date : String) : Boolean {
        val regex = Regex("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
        return date.matches(regex)
    }

}