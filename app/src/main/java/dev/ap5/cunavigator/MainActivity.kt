package dev.ap5.cunavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.ap5.cunavigator.data.components.MTDApiService
import dev.ap5.cunavigator.ui.composables.NavigationFrame
import dev.ap5.cunavigator.ui.theme.CUNavigatorTheme
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@Inject
	lateinit var mtdApiService : MTDApiService

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)

		val stops = runBlocking {
			val mtd = mtdApiService.service
			return@runBlocking mtd.getStops().unwrap()!!
		}

		setContent {
			CUNavigatorTheme {
				NavigationFrame()
			}
		}
	}
}