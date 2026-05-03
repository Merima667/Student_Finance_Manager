package com.example.student_finance_manager_app.presentation.viewmodel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.repository.FakeTransactionRepository
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
class TransactionViewModel @Inject constructor() : ViewModel() {
    private val repository = FakeTransactionRepository()
    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Init)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TransactionNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var allTransactions = listOf<Transaction>()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            try {
                val data = repository.getTransactions()
                allTransactions = data
                _uiState.value = TransactionUiState.Success(
                    transactions = allTransactions,
                    searchQuery = ""
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = TransactionUiState.Error(
                    e.message ?: "Failed to load trnasactions."
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        val filtered = allTransactions.filter {
            it.title.contains(query, ignoreCase = true)
        }
        _uiState.value = TransactionUiState.Success(
            transactions = filtered,
            searchQuery = query
        )
    }

    fun resetUiState() {
        loadTransactions()
    }
}