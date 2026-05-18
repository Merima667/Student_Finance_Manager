package com.example.student_finance_manager_app.presentation.viewmodel.add_transaction

sealed interface AddTransactionNavigationEvent {
    data object Navigate : AddTransactionNavigationEvent
    data object NavigateBack : AddTransactionNavigationEvent
}