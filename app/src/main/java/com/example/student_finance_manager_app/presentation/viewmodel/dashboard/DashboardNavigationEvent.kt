package com.example.student_finance_manager_app.presentation.viewmodel.dashboard

sealed interface DashboardNavigationEvent {
    data object Navigate : DashboardNavigationEvent
    data object NavigateBack : DashboardNavigationEvent
}