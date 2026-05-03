package com.example.student_finance_manager_app.presentation.viewmodel.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AddTransactionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<AddTransactionUiState>(AddTransactionUiState.Init)
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<AddTransactionNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun addTransaction(
        title: String,
        amount: String,
        type: TransactionType
    ) {
        viewModelScope.launch {
            _uiState.value = AddTransactionUiState.Loading
            when {
                title.isBlank() -> {
                    _uiState.value = AddTransactionUiState.Error("Naziv ne može biti prazan")
                }
                amount.isBlank() -> {
                    _uiState.value = AddTransactionUiState.Error("Iznos ne može biti prazan")
                }
                amount.toDoubleOrNull() == null -> {
                    _uiState.value = AddTransactionUiState.Error("Iznos mora biti broj")
                }
                amount.toDouble() <= 0 -> {
                    _uiState.value = AddTransactionUiState.Error("Iznos mora biti veći od 0")
                }
                else -> {
                    _uiState.value = AddTransactionUiState.Success
                    //transactionrepository.addTransaction(title, amount);
                    _navigationEvent.send(AddTransactionNavigationEvent.NavigateBack)
                }
            }
        }
    }

    fun resetUiState() {
        _uiState.value = AddTransactionUiState.Init
    }
}