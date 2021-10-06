package dev.seren.Managers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.game.GameDataList

/**
 * Interacting with Game endpoint
 * */
@Suppress("unused")
class Game : BallManager() {

    val today = getCurrentDateTime().toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    internal val cache = BallCache<Int, GameData>(60)

    /**
     * Method to confirm date is in a correct YYYY-MM-DD
     */
    fun isValidDate(date : String) : Boolean = date.matches("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])".toRegex())

    val isGameToday : Boolean
      get() =  fetch("$basePath/games?dates[]=$today") {
            val type = object : TypeToken<GameDataList>() {}.type
            val response = Gson().fromJson<GameDataList>(this, type)
            return@fetch response.data.isEmpty()
        }!!


    fun fetchById(id: Int) : GameData? {
        return if (cache hasKey id) cache[id]
        else fetch("${basePath}/games/$id") {
            val data = gson.fromJson(this, GameData::class.java)
            cache[id] = data

            return@fetch data
        }

    }

    /**
     * @param [String] - Date in "yyyy-MM-dd" format
     */
    fun fetchByDate(date: String = today) : List<GameData>? {
        return fetch("$basePath/games?dates[]=$date") {
            val type = object : TypeToken<GameDataList>(){}.type
            val (data) = gson.fromJson<GameDataList>(this, type)
                .also { (data) ->
                    data.forEach {
                        cache[it.id] = it
                    }
                }

            return@fetch data
        }
    }






}