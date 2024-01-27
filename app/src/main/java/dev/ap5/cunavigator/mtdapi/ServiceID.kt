package dev.ap5.cunavigator.mtdapi

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class ServiceID(private val id : String)