package dev.ap5.cunavigator.data

import javax.inject.Inject

class SecretsRepository @Inject constructor() {
    fun mtdKey() : String = "REDACTED_MTD_API_KEY"
}