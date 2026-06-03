package pl.atk.bikegoalwidget.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppBorders(
    val thin: Dp = 1.dp,
    val medium: Dp = 2.dp,
    val thick: Dp = 3.dp,

    val cardDefault: Dp = 1.dp,
    val cardSelected: Dp = 3.dp
)

val LocalBorders = staticCompositionLocalOf { AppBorders() }