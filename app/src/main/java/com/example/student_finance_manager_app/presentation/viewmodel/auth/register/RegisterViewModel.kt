package com.example.student_finance_manager_app.presentation.viewmodel.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Init)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<RegisterNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onRegisterClick(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            delay(1500)
            if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                _uiState.value = RegisterUiState.Error("Please fill in all fields.")
            } else {
                _uiState.value = RegisterUiState.Success
                _navigationEvent.send(RegisterNavigationEvent.Navigate)
            }
        }
    }
    fun resetUiState() {
        _uiState.value = RegisterUiState.Init
    }
}