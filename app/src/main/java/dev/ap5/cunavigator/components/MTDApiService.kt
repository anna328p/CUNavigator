package dev.ap5.cunavigator.components

import dev.ap5.cunavigator.mtdapi.MTDApi
import javax.inject.Inject

class MTDApiService @Inject constructor(
    private val mtdRetrofit : MTDRetrofit
) {
    val service : MTDApi by lazy {
        mtdRetrofit.service.create(MTDApi::class.java)
    }
}