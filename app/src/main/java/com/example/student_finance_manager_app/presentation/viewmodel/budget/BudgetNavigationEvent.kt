package com.example.student_finance_manager_app.presentation.viewmodel.budget

sealed interface BudgetNavigationEvent {
    data object Navigate : BudgetNavigationEvent
    data object NavigateBack : BudgetNavigationEvent
}