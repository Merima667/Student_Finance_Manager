package com.example.student_finance_manager_app.presentation.viewmodel.add_transaction

sealed interface AddTransactionUiState {
    data object Init : AddTransactionUiState
    data object Loading : AddTransactionUiState
    data object Success : AddTransactionUiState
    data class Error(val message: String) : AddTransactionUiState
}