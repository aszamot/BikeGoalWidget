package pl.atk.bikegoalwidget.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class AppShapes(
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(10.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),

    val card: RoundedCornerShape = RoundedCornerShape(10.dp)
)

val LocalShapes = staticCompositionLocalOf { AppShapes() }