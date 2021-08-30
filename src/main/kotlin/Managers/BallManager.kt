package dev.seren.Managers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.github.kittinunf.fuel.gson.responseObject

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getOrElse
import com.github.kittinunf.result.getOrNull
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.game.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.system.exitProcess


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
     * https://www.balldontlie.io/api/v1
     */
   protected open val basePath = client.basePath!!

   protected inline fun fetch(url : String, crossinline withAction : String.() -> Unit)  {

       url
            .httpGet()
            .responseString { result ->
                when(result) {
                    is Result.Success -> {
                           result.get().withAction()
                    }

                }

            }.join()

       }

 }





