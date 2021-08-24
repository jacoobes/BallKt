package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response

import com.github.kittinunf.fuel.httpGet



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



}





