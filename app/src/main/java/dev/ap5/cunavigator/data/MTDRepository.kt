package dev.ap5.cunavigator.data

import dev.ap5.cunavigator.components.MTDApiService
import dev.ap5.cunavigator.mtdapi.MTDApi
import javax.inject.Inject

class MTDRepository @Inject constructor(
    private val apiService : MTDApiService
) {
    private val mtd : MTDApi by lazy {
        apiService.service
    }
}