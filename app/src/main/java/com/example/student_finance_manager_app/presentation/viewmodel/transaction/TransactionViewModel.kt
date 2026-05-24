package com.example.student_finance_manager_app.presentation.viewmodel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.repository.TransactionNetworkRepository
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
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val networkRepository: TransactionNetworkRepository
) : ViewModel() {
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
                repository.getAllTransactions().collect { data ->
                    allTransactions = data
                    _uiState.value = TransactionUiState.Success(
                        transactions = data,
                        searchQuery = ""
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = TransactionUiState.Error(
                    e.message ?: "Failed to load transactions."
                )
            }
        }
    }

    fun loadTransactionFromNetwork() {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            try {
                val transactions = networkRepository.getTransactions()
                _uiState.value = TransactionUiState.Success(
                    transactions = transactions,
                    searchQuery = ""
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TransactionUiState.Error(
                    e.message ?: "Failed to load transactions from network."
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

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    fun resetUiState() {
        loadTransactions()
    }
}