package dev.ap5.cunavigator.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavTab<RouteType: Any> (
    val labelResource: Int,
    val route: RouteType,
    val iconInactive: ImageVector,
    val iconActive: ImageVector
)