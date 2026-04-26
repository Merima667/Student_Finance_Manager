package com.example.student_finance_manager_app.presentation.viewmodel.dashboard

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Init)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<DashboardNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            val transactions = HardcodedData.defaultTransactions
            val name = HardcodedData.defaultUserProfile.name
            val totalIncome = transactions
                .filter { it.type == TransactionType.INCOME }
                .sumOf { it.amount }
            val totalExpanses = transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val balance = totalIncome - totalExpanses

            _uiState.value = DashboardUiState.Success(
                dashboardData = DashboardData(
                    name = name,
                    totalIncome = totalIncome,
                    totalExpanses = totalExpanses,
                    balance = balance,
                    transactions = transactions
                )
            )
        }
    }

    fun resetUiState() {
        _uiState.value = DashboardUiState.Init
    }
}