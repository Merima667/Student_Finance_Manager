package com.example.student_finance_manager_app.presentation.viewmodel.profile

sealed interface ProfileNavigationEvent {
    data object Navigate : ProfileNavigationEvent
    data object NavigateBack : ProfileNavigationEvent
}