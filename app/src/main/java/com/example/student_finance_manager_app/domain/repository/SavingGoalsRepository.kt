package com.example.student_finance_manager_app.domain.repository

import com.example.student_finance_manager_app.domain.data.SavingsGoal
import kotlinx.coroutines.flow.Flow

interface SavingsGoalRepository {
    fun getAllSavingsGoals(): Flow<List<SavingsGoal>>
    suspend fun insertSavingsGoal(goal: SavingsGoal)
    suspend fun updateSavingsGoal(goal: SavingsGoal)
    suspend fun deleteSavingsGoal(goal: SavingsGoal)
}