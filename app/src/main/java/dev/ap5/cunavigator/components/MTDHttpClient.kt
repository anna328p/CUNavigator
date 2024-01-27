package dev.ap5.cunavigator.components

import dev.ap5.cunavigator.data.SecretsRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Inject

class MTDHttpClient @Inject constructor(
    private val secrets : SecretsRepository
) {
    val service = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain -> chain.proceed(
            chain.request().newBuilder()
                .url(chain.request().url.newBuilder()
                    .addQueryParameter("key", secrets.mtdKey())
                    .build())
                .build())
        } )
        .build()
}