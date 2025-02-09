package dev.ap5.cunavigator.ui.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.ap5.cunavigator.ui.navigation.NavTab
import dev.ap5.cunavigator.ui.navigation.NavTabs
import dev.ap5.cunavigator.ui.navigation.TopLevel
import androidx.navigation.NavDestination.Companion.hasRoute
import dev.ap5.cunavigator.ui.composables.pages.HomePane
import dev.ap5.cunavigator.ui.composables.pages.MapPane
import dev.ap5.cunavigator.ui.composables.pages.NavigatePane
import dev.ap5.cunavigator.ui.composables.pages.SavedPane

@Composable
fun RowScope.NavTabItem(
    tab: NavTab<out Any>,
    selected: Boolean,
    onClick: () -> Unit
) {
    val stateIcon = if (selected) tab.iconActive else tab.iconInactive
    val labelText = stringResource(tab.labelResource)

    NavigationBarItem(
        icon = {
            Icon(
                stateIcon,
                contentDescription = labelText,
            )
        },
        label = { Text(labelText) },
        selected = selected,
        onClick = onClick
    )
}

@Composable
fun BottomNav(navController: NavController) {
    NavigationBar {
        val currentBSEntry by navController.currentBackStackEntryAsState()
        val currentDest = currentBSEntry?.destination

        val isSelected = { tab: NavTab<out Any> ->
            currentDest?.hierarchy?.any { it.hasRoute(route = tab.route::class) } == true
        }

        NavTabs.allTabs.forEach { tab ->
            NavTabItem(tab, selected = isSelected(tab)) {
                navController.navigate(tab.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(content = {
        BottomNav(navController)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NavigationFrame() {
    var navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevel.Home,
            modifier = Modifier.fillMaxSize()
        ) {
            for (tab in NavTabs.allTabs) {
                when (tab.route) {
                    is TopLevel.Home ->
                        composable<TopLevel.Home> { HomePane(navController) }
                    is TopLevel.Map ->
                        composable<TopLevel.Map> { MapPane(navController) }
                    is TopLevel.Navigate ->
                        composable<TopLevel.Navigate> { NavigatePane(navController) }
                    is TopLevel.Saved ->
                        composable<TopLevel.Saved> { SavedPane(navController) }
                }
            }
        }
    }
}