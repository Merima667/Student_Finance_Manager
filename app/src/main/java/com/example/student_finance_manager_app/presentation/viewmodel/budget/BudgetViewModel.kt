package com.example.student_finance_manager_app.presentation.viewmodel.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.Category
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
class BudgetViewModel @Inject constructor() : ViewModel() {
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

            val transactions = HardcodedData.defaultTransactions
            val monthlyBudget = HardcodedData.defaultUserProfile.monthlyBudget

            val spentOnFood = transactions
                .filter { it.category == Category.FOOD && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnTransport = transactions
                .filter { it.category == Category.TRANSPORT && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnEducation = transactions
                .filter { it.category == Category.EDUCATION && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnEntertainment = transactions
                .filter { it.category == Category.ENTERTAINMENT && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnHealth = transactions
                .filter { it.category == Category.HEALTH && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnOther = transactions
                .filter { it.category == Category.OTHER && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }

            _uiState.value = BudgetUiState.Success(
                budgetData = BudgetData(
                    monthlyBudget = monthlyBudget,
                    spentOnFood = spentOnFood,
                    spentOnTransport = spentOnTransport,
                    spentOnEducation = spentOnEducation,
                    spentOnEntertainment = spentOnEntertainment,
                    spentOnHealth = spentOnHealth,
                    spentOnOther = spentOnOther
                )
            )
        }
    }

    fun resetUiState() {
        _uiState.value = BudgetUiState.Init
    }
}