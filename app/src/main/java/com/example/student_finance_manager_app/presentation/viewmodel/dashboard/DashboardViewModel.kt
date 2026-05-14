package com.example.student_finance_manager_app.presentation.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.model.repository.TransactionRepository
import com.example.student_finance_manager_app.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userProfileRepository: UserProfileRepository
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
            try {
                combine(
                    transactionRepository.getAllTransactions(),
                    userProfileRepository.getUserProfile()
                ) { transactions, userProfiles ->
                    val userProfile = userProfiles.firstOrNull()
                    val totalIncome = transactions
                        .filter { it.type == TransactionType.INCOME }
                        .sumOf { it.amount }
                    val totalExpenses = transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .sumOf { it.amount }
                    val balance = totalIncome - totalExpenses

                    DashboardData(
                        name = userProfile?.name ?: "Student",
                        totalIncome = totalIncome,
                        totalExpanses = totalExpenses,
                        balance = balance,
                        transactions = transactions
                    )
                }.collect { dashboardData ->
                    _uiState.value = DashboardUiState.Success(dashboardData = dashboardData)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
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