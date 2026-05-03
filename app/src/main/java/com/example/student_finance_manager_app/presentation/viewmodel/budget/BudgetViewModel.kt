package com.example.student_finance_manager_app.presentation.viewmodel.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.repository.FakeBudgetRepository
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
class BudgetViewModel @Inject constructor() : ViewModel() {
    private val repository = FakeBudgetRepository()
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
                val data = repository.getBudgetData()
                _uiState.value = BudgetUiState.Success(
                    budgetData = BudgetData(
                        monthlyBudget = data.monthlyBudget,
                        spentOnFood = data.spentOnFood,
                        spentOnTransport = data.spentOnTransport,
                        spentOnEducation = data.spentOnEducation,
                        spentOnEntertainment = data.spentOnEntertainment,
                        spentOnHealth = data.spentOnHealth,
                        spentOnOther = data.spentOnOther
                    )
                )
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