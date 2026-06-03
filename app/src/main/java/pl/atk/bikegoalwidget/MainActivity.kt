package pl.atk.bikegoalwidget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pl.atk.bikegoalwidget.presentation.screens.dashboard.DashboardScreen
import pl.atk.bikegoalwidget.theme.BikeGoalWidgetTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BikeGoalWidgetTheme {
                BikeWithGoalApp(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun BikeWithGoalApp(modifier: Modifier = Modifier) {
    DashboardScreen()
}