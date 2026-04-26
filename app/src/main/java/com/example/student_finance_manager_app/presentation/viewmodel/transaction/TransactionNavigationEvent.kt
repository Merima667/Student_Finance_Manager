package com.example.student_finance_manager_app.presentation.viewmodel.transaction

sealed interface TransactionNavigationEvent {
    data object Navigate : TransactionNavigationEvent
    data object NavigateBack : TransactionNavigationEvent
}