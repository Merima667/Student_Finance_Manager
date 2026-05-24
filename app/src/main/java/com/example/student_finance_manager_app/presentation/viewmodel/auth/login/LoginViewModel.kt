package com.example.student_finance_manager_app.presentation.viewmodel.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.repository.AuthRepository
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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<LoginNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        if(authRepository.isUserLoggerIn()) {
            viewModelScope.launch {
                _uiState.value = LoginUiState.Success(isLoggedIn = true)
                _navigationEvent.send(LoginNavigationEvent.Navigate)
            }
        }
    }

    fun onLoginClick(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                authRepository.login(email, password)
                _uiState.value = LoginUiState.Success(isLoggedIn = true)
                _navigationEvent.send(LoginNavigationEvent.Navigate)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Greška pri prijavi"
                )
            }
        }
    }
    fun resetUiState() {
        _uiState.value = LoginUiState.Init
    }
}