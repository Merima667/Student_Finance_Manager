package com.example.student_finance_manager_app.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.domain.data.TransactionType
import com.example.student_finance_manager_app.domain.repository.AuthRepository
import com.example.student_finance_manager_app.domain.repository.TransactionRepository
import com.example.student_finance_manager_app.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userProfileRepository: UserProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

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
            try {
                val transactionsDeferred = async { transactionRepository.getAllTransactions().first() }
                val userProfileDeferred = async { userProfileRepository.getUserProfile().first() }

                val transactions = transactionsDeferred.await()
                val userProfiles = userProfileDeferred.await()
                val userProfile = userProfiles.firstOrNull()

                val totalIncome = transactions
                    .filter { it.type == TransactionType.INCOME }
                    .sumOf { it.amount }
                val totalExpenses = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }

                _uiState.value = ProfileUiState.Success(
                    profileData = ProfileData(
                        name = userProfile?.name ?: "Student",
                        monthlyBudget = userProfile?.monthlyBudget ?: 0.0,
                        totalTransactions = transactions.size.toDouble(),
                        totalIncome = totalIncome,
                        totalExpenses = totalExpenses,
                        categories = emptyList()
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(
                    e.message ?: "Failed to load profile."
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _navigationEvent.send(ProfileNavigationEvent.Logout)
        }
    }

    fun resetUiState() {
        loadProfile()
    }
}