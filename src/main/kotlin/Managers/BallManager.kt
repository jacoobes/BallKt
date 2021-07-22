package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.player.PlayerList
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*


typealias Promise<T> = Deferred<T>

/**
 * Base class for all endpoints with the API
 * Cannot be instantiated outside of the package
 *
 */

 sealed class BallManager {

   open val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }


    protected val errorActionMap = mapOf(
        400 to "Bad Request -- Your request is invalid.",
        404 to "Not Found -- The specified resource could not be found.",
        406 to "Not Acceptable -- You requested a format that isn't json.",
        429 to "Too Many Requests -- Rate limit",
        500 to "Internal Server Error -- We had a problem with our server. Try again later.",
        503 to "Service Unavailable -- We're temporarily offline for maintenance. Please try again later."
    )


    internal open val baseUrl : String = "https://www.balldontlie.io/api/v1/"

    /**
     * @param [url] - raw api calls
     * @return [HttpResponse]
     */
    private suspend fun fetch(url: String = baseUrl) : HttpResponse = client.use {
        client.get(url) {
            accept(Json)
        }
    }

    /**
     * Runs on IO thread for networking calls.
     * Checks to see if their is an error and throws
     * @returns [Promise<String>] body of response as a String
     */
    internal suspend fun <T> extractBody(url: String): Promise<String> = coroutineScope {
        async(Dispatchers.IO) {
            val response = fetch(url)
            val responseCode = response.status.value
            errorActionMap[responseCode]?.let {
                throw Error(errorActionMap[responseCode])
            }
            response.readText()
        }
    }

    /**
     * Turns [extractBody] into JSON with serialization
     */
    protected abstract suspend fun <T> JSONResponse(url : String = baseUrl, forList: Boolean = false) : T


}





