package dev.ap5.cunavigator.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Navigation
import dev.ap5.cunavigator.R

object NavTabs {
    val Home = NavTab(
        labelResource = R.string.tab_home,
        route = TopLevel.Home,
        iconInactive = Icons.Outlined.Home,
        iconActive = Icons.Filled.Home
    )

    val Map = NavTab(
        labelResource = R.string.tab_map,
        route = TopLevel.Map,
        iconInactive = Icons.Outlined.Map,
        iconActive = Icons.Filled.Map
    )

    val Navigate = NavTab(
        labelResource = R.string.tab_navigate,
        route = TopLevel.Navigate,
        iconInactive = Icons.Outlined.Navigation,
        iconActive = Icons.Filled.Navigation
    )

    val Saved = NavTab(
        labelResource = R.string.tab_saved,
        route = TopLevel.Saved,
        iconInactive = Icons.Outlined.Bookmarks,
        iconActive = Icons.Filled.Bookmarks
    )

    val allTabs = arrayOf(Home, Map, Navigate, Saved)
}