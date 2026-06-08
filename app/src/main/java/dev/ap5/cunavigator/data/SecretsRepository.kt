package dev.ap5.cunavigator.data

import dev.ap5.cunavigator.BuildConfig
import javax.inject.Inject

class SecretsRepository @Inject constructor() {
    fun mtdKey() : String = BuildConfig.MTD_API_KEY
}