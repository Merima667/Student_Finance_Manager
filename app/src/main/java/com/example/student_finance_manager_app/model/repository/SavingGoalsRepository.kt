package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.entity.SavingsGoalEntity
import kotlinx.coroutines.flow.Flow

interface SavingsGoalRepository {
    fun getAllSavingsGoals(): Flow<List<SavingsGoalEntity>>
    suspend fun insertSavingsGoal(goal: SavingsGoalEntity)
    suspend fun updateSavingsGoal(goal: SavingsGoalEntity)
    suspend fun deleteSavingsGoal(goal: SavingsGoalEntity)
}