package pl.atk.bikegoalwidget.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    val paddingXs: Dp = 4.dp,
    val paddingSm: Dp = 8.dp,
    val paddingMd: Dp = 16.dp,
    val paddingLg: Dp = 24.dp,

    val spacingXs: Dp = 4.dp,
    val spacingSm: Dp = 8.dp,
    val spacingMd: Dp = 16.dp,
    val spacingLg: Dp = 24.dp,

    val iconMd: Dp = 16.dp
)

val LocalDimens = staticCompositionLocalOf { Dimens() }