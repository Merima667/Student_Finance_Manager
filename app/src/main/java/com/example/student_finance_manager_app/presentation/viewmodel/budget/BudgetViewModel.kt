package com.example.student_finance_manager_app.presentation.viewmodel.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.model.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: TransactionRepository
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
            try{
                repository.getAllTransactions().collect { transactions ->
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

                    _uiState.value = BudgetUiState.Success(
                        budgetData = BudgetData(
                            monthlyBudget = 1000.0,
                            spentOnFood = spentOnFood,
                            spentOnTransport = spentOnTransport,
                            spentOnEducation = spentOnEducation,
                            spentOnEntertainment = spentOnEntertainment,
                            spentOnHealth = spentOnHealth,
                            spentOnOther = spentOnOther
                        )
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
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