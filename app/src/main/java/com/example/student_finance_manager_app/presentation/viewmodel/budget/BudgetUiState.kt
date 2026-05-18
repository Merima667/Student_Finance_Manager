package com.example.student_finance_manager_app.presentation.viewmodel.budget

data class BudgetData(
    val monthlyBudget: Double,
    val spentOnFood: Double,
    val spentOnTransport: Double,
    val spentOnEducation: Double,
    val spentOnEntertainment: Double,
    val spentOnHealth: Double,
    val spentOnOther: Double
)

sealed interface BudgetUiState {
    data object Init : BudgetUiState
    data object Loading : BudgetUiState
    data class Success(val budgetData: BudgetData) : BudgetUiState
    data class Error(val message: String) : BudgetUiState
}