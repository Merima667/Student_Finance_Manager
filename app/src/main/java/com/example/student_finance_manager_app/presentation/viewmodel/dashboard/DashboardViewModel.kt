package com.example.student_finance_manager_app.presentation.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.model.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Init)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<DashboardNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _financeTip = MutableStateFlow("")
    val financeTip: StateFlow<String> = _financeTip.asStateFlow()

    init {
        loadDashboardData()
        startFinanceTips()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            try{
                repository.getAllTransactions().collect { transactions ->
                    val totalIncome = transactions
                        .filter { it.type == TransactionType.INCOME }
                        .sumOf { it.amount }
                    val totalExpenses = transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .sumOf { it.amount }
                    val balance = totalIncome - totalExpenses

                    _uiState.value = DashboardUiState.Success(
                        dashboardData = DashboardData(
                            name = "Student",
                            totalIncome = totalIncome,
                            totalExpanses = totalExpenses,
                            balance = balance,
                            transactions = transactions
                        )
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = DashboardUiState.Error(
                    e.message ?: "Failed to load dashboard."
                )
            }
        }
    }

    private fun observeFinanceTips(): Flow<String> = flow {
        emit("Track your daily expenses to stay on budget")
        delay(3000)
        emit("Set a monthly budget for each category")
        delay(3000)
        emit("Save at least 20% of your monthly income")
    }

    private fun startFinanceTips() {
        viewModelScope.launch {
            observeFinanceTips().collect { tip ->
                _financeTip.value = tip
            }
        }
    }

    fun resetUiState() {
        loadDashboardData()
    }
}