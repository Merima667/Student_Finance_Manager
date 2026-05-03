package com.example.student_finance_manager_app.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_manager_app.model.repository.FakeProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val repository = FakeProfileRepository()
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
                val categoriesDeferred = async { repository.getProfileCategories() }
                val statsDeferred = async { repository.getProfileStats() }
                val profileDeferred = async { repository.getProfileData() }

                val categories = categoriesDeferred.await()
                val stats = statsDeferred.await()
                val profile = profileDeferred.await()

                _uiState.value = ProfileUiState.Success(
                    profileData = ProfileData(
                        name = profile.name,
                        monthlyBudget = profile.monthlyBudget,
                        totalTransactions = stats.totalTransactions,
                        totalIncome = stats.totalIncome,
                        totalExpenses = stats.totalExpenses,
                        categories = categories
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = ProfileUiState.Error(
                    e.message ?: "Failed to load profile."
                )
            }
        }
    }

    fun resetUiState() {
        loadProfile()
    }
}