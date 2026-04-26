package com.example.student_finance_manager_app.presentation.viewmodel.transaction

import com.example.student_finance_manager_app.model.Transaction
import java.time.temporal.TemporalQuery

sealed interface TransactionUiState {
    data object Init : TransactionUiState
    data object Loading : TransactionUiState
    data class Success(
        val transactions: List<Transaction>,
        val searchQuery: String
    ) : TransactionUiState
    data class Error(val message: String) : TransactionUiState
}