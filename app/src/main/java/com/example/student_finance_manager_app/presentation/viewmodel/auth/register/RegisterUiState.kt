package com.example.student_finance_manager_app.presentation.viewmodel.auth.register

sealed interface RegisterUiState {
    data object Init : RegisterUiState
    data object Loading : RegisterUiState
    data object Success : RegisterUiState
    data class Error(val message: String) : RegisterUiState
}