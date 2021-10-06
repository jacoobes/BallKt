package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*


/**
 * Base class for all endpoints with the API
 * Cannot be instantiated outside the package
 *
 */

sealed class BallManager {
    protected val gson = Gson()
    private val client = FuelManager.instance.apply {
        basePath = "https://www.balldontlie.io/api/v1"
    }


    /**
     * https://www.balldontlie.io/api/v1
     */
    protected val basePath = client.basePath!!

    protected inline fun <T> fetch(url: String, crossinline withAction: String.() -> T?) : T? {
    var response : T? = null

        url.httpGet()
            .responseString { result ->
                when (result) {
                    is Result.Success -> response = result.get().withAction()
                    is Result.Failure -> println("Bad Request!").also { println(result.getException()) }
                }
            }.join()

        return response
    }

    protected fun constructQuery(season: Int = Year.now().value, vararg playerIds: Int): String {
        return buildString {
            append("$basePath/season_averages")
            append("?season=$season")
            for (id in playerIds) {
                append("&player_ids[]=$id")
            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date = Calendar.getInstance().time

}





