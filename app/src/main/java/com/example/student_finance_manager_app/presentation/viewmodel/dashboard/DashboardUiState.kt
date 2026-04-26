package com.example.student_finance_manager_app.presentation.viewmodel.dashboard

import com.example.student_finance_manager_app.model.Transaction

data class DashboardData(
    val name: String,
    val totalIncome: Double,
    val totalExpanses: Double,
    val balance: Double,
    val transactions: List<Transaction>
)

sealed interface DashboardUiState {
    data object Init : DashboardUiState
    data object Loading : DashboardUiState
    data class Success(val dashboardData: DashboardData) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}