package dev.ap5.cunavigator.components

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.ap5.cunavigator.mtdapi.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

class MTDRetrofit @Inject constructor(
    private val mtdHttpClient: MTDHttpClient,
) {
    val service: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.BASE_URL)
        .client(mtdHttpClient.service)
        .validateEagerly(true)
        .build()
}