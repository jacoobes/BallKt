package dev.seren.Managers

import io.ktor.client.*

import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import io.ktor.http.ContentType.Application.Json
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import java.lang.Error
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

    internal val errorActionMap = mapOf(
        400 to "Bad Request -- Your request is invalid.",
        404 to "Not Found -- The specified resource could not be found.",
        406 to "Not Acceptable -- You requested a format that isn't json.",
        429 to "Too Many Requests -- Rate limit",
        500 to "Internal Server Error -- We had a problem with our server. Try again later.",
        503 to "Service Unavailable -- We're temporarily offline for maintenance. Please try again later."
    )


    internal open val baseUrl : String = "https://www.balldontlie.io/api/v1/"

    /**
     * raw api calls
     * body is a string that needs to serialized with class specific serializers
     * kotlinx.serialization.json.decodeFromString()
     * @return [HttpResponse]
     *
     */
    internal suspend fun fetch(url: String = baseUrl) : HttpResponse = client.use {
        client.get(url) {
            accept(Json)
        }
    }

    /* *
     * extracts body of response as [String]
     * Checks to see if their is an error and throws Respectively
     */

    internal suspend fun extractBody(url: String): Promise<String> = withContext(Dispatchers.IO) {

        val responseCode = fetch(url).status.value

        if(errorActionMap[responseCode] != null) throw Error(errorActionMap[responseCode])

            val responseBody =  async { fetch().readText() }


      responseBody
    }



}

