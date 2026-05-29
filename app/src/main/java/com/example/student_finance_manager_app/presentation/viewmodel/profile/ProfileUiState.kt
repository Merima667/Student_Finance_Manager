package com.example.student_finance_manager_app.presentation.viewmodel.profile

import com.example.student_finance_manager_app.domain.data.CategoryItem

data class ProfileData(
    val name: String,
    val monthlyBudget: Double,
    val totalTransactions: Double,
    val totalIncome: Double,
    val totalExpenses: Double,
    val categories: List<CategoryItem>
)

sealed interface ProfileUiState {
    data object Init : ProfileUiState
    data object Loading : ProfileUiState
    data class Success(val profileData: ProfileData) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}