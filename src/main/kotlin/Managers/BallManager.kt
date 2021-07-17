package dev.seren.Managers

import io.ktor.client.*

import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import io.ktor.http.ContentType.Application.Json
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.*



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

    internal open val baseUrl : String = "https://www.balldontlie.io/api/v1/"

    /**
     * raw api calls
     * body is a string that needs to serialized with class specific serializers
     * kotlinx.serialization.json.decodeFromString()
     */
    open suspend fun fetch() : HttpResponse = client.use {
        client.get(baseUrl) {
            accept(Json)
        }
    }

}

