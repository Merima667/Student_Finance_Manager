package com.example.student_finance_manager_app.presentation.viewmodel.auth.login

sealed interface LoginNavigationEvent {
    data object Navigate : LoginNavigationEvent
    data object NavigateBack : LoginNavigationEvent
}