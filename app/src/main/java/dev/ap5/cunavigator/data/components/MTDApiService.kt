package dev.ap5.cunavigator.data.components

import dev.ap5.cunavigator.data.SecretsRepository
import dev.ap5.mtdapi.rest.MTDApiClient
import javax.inject.Inject

class MTDApiService @Inject constructor(
    private val secrets : SecretsRepository
) {
    val service = MTDApiClient(secrets.mtdKey())
}
