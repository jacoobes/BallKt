package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.github.kittinunf.fuel.httpGet
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import com.github.kittinunf.result.Result


/**
 * Base class for all endpoints with the API
 * Cannot be instantiated outside of the package
 *
 */

 sealed class BallManager {

   protected val client = FuelManager.instance.apply {
       basePath = "https://www.balldontlie.io/api/v1"
   }


    /**
     * Runs on IO thread for networking calls.
     * Checks to see if their is an error and throws
     * @returns [Response] body of response as a String
     */
    internal fun extractBody(url: String): Request = url.httpGet()




}





