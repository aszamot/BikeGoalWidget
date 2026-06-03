package pl.atk.bikegoalwidget.presentation.screens.dashboard

import kotlinx.serialization.Serializable

@Serializable
sealed interface DashboardRoute {
    @Serializable
    data object Dashboard: DashboardRoute
}