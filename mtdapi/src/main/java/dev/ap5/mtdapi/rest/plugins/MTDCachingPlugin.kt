package dev.ap5.mtdapi.rest.plugins

import dev.ap5.mtdapi.rest.misc.CachePolicy
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.util.AttributeKey

val MTDCachingPlugin = createClientPlugin("MTDCachingPlugin") {
    onRequest { request, _ ->
        val cachePolicy : CachePolicy? = request.attributes[AttributeKey("cachePolicy")]


    }
}