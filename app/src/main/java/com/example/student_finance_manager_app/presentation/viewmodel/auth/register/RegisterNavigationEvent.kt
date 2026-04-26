package com.example.student_finance_manager_app.presentation.viewmodel.auth.register

sealed interface RegisterNavigationEvent {
    data object Navigate : RegisterNavigationEvent
    data object NavigateBack : RegisterNavigationEvent
}