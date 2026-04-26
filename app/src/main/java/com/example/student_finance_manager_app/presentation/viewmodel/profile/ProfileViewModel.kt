package com.example.student_finance_manager_app.presentation.viewmodel.profile

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
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ProfileNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val name = HardcodedData.defaultUserProfile.name
            val monthlyBudget = HardcodedData.defaultUserProfile.monthlyBudget
            val transactions = HardcodedData.defaultTransactions
            val totalTransactions = transactions.size.toDouble()
            val totalIncome = transactions
                .filter { it.type == TransactionType.INCOME }
                .sumOf { it.amount }
            val totalExpenses = transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val categories = HardcodedData.defaultCategories

            _uiState.value = ProfileUiState.Success(
                profileData = ProfileData(
                    name = name,
                    monthlyBudget = monthlyBudget,
                    totalTransactions = totalTransactions,
                    totalIncome = totalIncome,
                    totalExpenses = totalExpenses,
                    categories = categories
                )
            )
        }
    }

    fun resetUiState() {
        _uiState.value = ProfileUiState.Init
    }
}