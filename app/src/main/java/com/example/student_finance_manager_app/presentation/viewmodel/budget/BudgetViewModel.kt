package com.example.student_finance_manager_app.presentation.viewmodel.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.domain.data.Category
import com.example.student_finance_manager_app.domain.data.TransactionType
import com.example.student_finance_manager_app.domain.repository.BudgetRepository
import com.example.student_finance_manager_app.domain.repository.TransactionRepository
import com.example.student_finance_manager_app.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BudgetUiState>(BudgetUiState.Init)
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<BudgetNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadBudgetData()
    }

    private fun loadBudgetData() {
        viewModelScope.launch {
            _uiState.value = BudgetUiState.Loading
            try {
                combine(
                    transactionRepository.getAllTransactions(),
                    userProfileRepository.getUserProfile()
                ) { transactions, userProfiles ->
                    val userProfile = userProfiles.firstOrNull()
                    val monthlyBudget = userProfile?.monthlyBudget ?: 1000.0
                    val expenses = transactions.filter { it.type == TransactionType.EXPENSE }

                    val spentOnFood = expenses
                        .filter { it.category == Category.FOOD }
                        .sumOf { it.amount }
                    val spentOnTransport = expenses
                        .filter { it.category == Category.TRANSPORT }
                        .sumOf { it.amount }
                    val spentOnEducation = expenses
                        .filter { it.category == Category.EDUCATION }
                        .sumOf { it.amount }
                    val spentOnEntertainment = expenses
                        .filter { it.category == Category.ENTERTAINMENT }
                        .sumOf { it.amount }
                    val spentOnHealth = expenses
                        .filter { it.category == Category.HEALTH }
                        .sumOf { it.amount }
                    val spentOnOther = expenses
                        .filter { it.category == Category.OTHER }
                        .sumOf { it.amount }

                    BudgetData(
                        monthlyBudget = monthlyBudget,
                        spentOnFood = spentOnFood,
                        spentOnTransport = spentOnTransport,
                        spentOnEducation = spentOnEducation,
                        spentOnEntertainment = spentOnEntertainment,
                        spentOnHealth = spentOnHealth,
                        spentOnOther = spentOnOther
                    )
                }.collect { budgetData ->
                    _uiState.value = BudgetUiState.Success(budgetData = budgetData)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = BudgetUiState.Error(
                    e.message ?: "Failed to load budget."
                )
            }
        }
    }

    fun resetUiState() {
        loadBudgetData()
    }
}