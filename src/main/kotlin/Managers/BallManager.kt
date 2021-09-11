package dev.seren.Managers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
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
     * https://www.balldontlie.io/api/v1
     */
    protected open val basePath = client.basePath!!

    protected inline fun <T> fetch(url: String, crossinline withAction: String.() -> T?) : T? {

        url.httpGet()
            .responseString { result ->
                when (result) {
                    is Result.Success -> {
                        result.get().withAction()
                    }
                    is Result.Failure -> println("Bad Request!")
                }

            }.join()

        return null
    }

}





