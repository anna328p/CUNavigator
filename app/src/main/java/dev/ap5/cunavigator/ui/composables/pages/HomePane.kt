package dev.ap5.cunavigator.ui.composables.pages

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.ap5.cunavigator.R
import dev.ap5.mtdapi.rest.models.Route
import dev.ap5.mtdapi.ids.RouteID
import dev.ap5.mtdapi.rest.models.Stop
import dev.ap5.cunavigator.ui.color
import dev.ap5.cunavigator.ui.composables.StopMap
import dev.ap5.cunavigator.ui.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader() {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Settings, "Settings")
            }
        }
    )
}

@Composable
fun NearbyStops() {
    val scrollState = ScrollState(0)
    val mPadding = Modifier.padding(8.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = mPadding
                .horizontalScroll(state = scrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("meowmeow")
            Text("meowmeow")
            Text("meowmeow")
            Text("meowmeow")
            Text("meowmeow")
            Text("meowmeow")
            Text("meowmeow")
        }
    }
}

@Composable
fun NearbyRoutes() {}

@Composable
fun StopView(stops: List<Stop>) {
    StopMap(stops)
}

@Composable
fun RouteBadge(route: Route) {
    Badge(
        containerColor = route.color(),
        contentColor = route.textColor()
    ) {
        Text(route.shortName)
    }
}

@Composable
fun StopList(stops: List<Pair<Stop, List<Route>>>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.stopListSpacing))
    ) {
        stops.forEach { (stop, routes) ->
            StopListItem(stop, routes)
        }
    }
}

@Composable
fun StopListItem(stop: Stop, routes: List<Route>) {
    ListItem(
        headlineContent = { Text(stop.name) },
        supportingContent = {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(dimensionResource(R.dimen.routeBadgeSpacing))
            ) {
                routes.forEach { route -> RouteBadge(route) }
            }
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("${stop.distance} mi")
                Icon(Icons.AutoMirrored.Outlined.ArrowForward, "View route")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchBar(modifier: Modifier) {
    var textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            state = textFieldState,
            onSearch = { expanded = false },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            placeholder = { Text(stringResource(R.string.global_search_hint)) },
            leadingIcon = { Icon(Icons.Outlined.Search, "Search") }
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchBar(
            inputField = { inputField() },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            Text("meow")
        }
    }
}

@Composable
fun FavoriteStops(modifier: Modifier) {
    Text(
        stringResource(R.string.favorite_stops),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )

    val stop = Stop(
        id = "IT",
        name = "Illinois Terminal",
        code = "MTD3121",
        stopPoints = listOf(),
        distance = 0.12
    )

    val green = Route(
        shortName = "50",
        id = RouteID("foo"),
        longName = "foo route",
        color = "008063",
        textColor = "ffffff"
    )

    StopList(listOf(stop to listOf(green, green)))
}

@Composable
fun HomePaneInner() {
    val scrollState = ScrollState(initial = 0)
    val mPadding = Modifier.padding(horizontal = 16.dp)
    val vSpacing = Arrangement.spacedBy(8.dp)

    Column(
        verticalArrangement = vSpacing,
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        FavoriteStops(modifier = mPadding)

        HorizontalDivider(
            modifier = mPadding
        )

        Text(
            modifier = mPadding,
            text = "meow"
        )
    }
}

@Composable
fun HomePane(navController: NavController) {
    Scaffold(
        topBar = {
            Column {
                HomeHeader()
                GlobalSearchBar(modifier = Modifier)
            }
        }
    ) { innerPadding ->
        Column(/* modifier = Modifier.padding(innerPadding) */) {
            HomePaneInner()
        }
    }
}

@Preview
@Composable
fun HomePanePreview() {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            HomePane(rememberNavController())
        }
    }
}