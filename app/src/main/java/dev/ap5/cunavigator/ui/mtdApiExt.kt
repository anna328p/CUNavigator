package dev.ap5.cunavigator.ui

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import dev.ap5.mtdapi.rest.models.Route

fun Route.color() : Color {
    return Color("#${this.color}".toColorInt())
}

fun Route.textColor() : Color {
    return Color("#${this.textColor}".toColorInt())
}