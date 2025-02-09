package dev.ap5.mtdapi.rest

import de.jensklingenberg.ktorfit.Ktorfit
import dev.ap5.mtdapi.rest.converters.MTDModelConverterFactory
import dev.ap5.mtdapi.rest.misc.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

/**
 * MTD API client.
 *
 * Implements [MTDApi] delegating to Ktorfit.
 *
 * @param apiKey  API key. Get one [here](https://developer.mtd.org/#SignUpForm).
 */
class MTDApiClient(apiKey: String) : MTDApi by buildClient(apiKey) {
    companion object {
        fun buildClient(apiKey: String) : MTDApi {
            val httpClient = buildHttpClient(apiKey)
            val ktorfit = buildKtorfit(httpClient)
            return buildService(ktorfit)
        }

        private fun buildService(ktorfit: Ktorfit) : MTDApi {
            return ktorfit.create()
        }

        private fun buildHttpClient(apiKey: String) : HttpClient {
            return HttpClient {
                defaultRequest {
                    url {
                        takeFrom(Constants.BASE_URL)
                        parameters.append("key", apiKey)
                    }
                }

                install(ContentNegotiation) { json() }
                install(Logging)
                install(HttpCache)
            }
        }

        private fun buildKtorfit(httpClient: HttpClient): Ktorfit {
            return Ktorfit.Builder()
                .httpClient(httpClient)
                .converterFactories(MTDModelConverterFactory())
                .build()
        }
    }
}