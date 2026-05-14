package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.dao.SavingsGoalDao
import com.example.student_finance_manager_app.model.data.local.entity.SavingsGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavingsGoalRepositoryImpl @Inject constructor(
    private val savingsGoalDao: SavingsGoalDao
) : SavingsGoalRepository {

    override fun getAllSavingsGoals(): Flow<List<SavingsGoalEntity>> {
        return savingsGoalDao.getAllSavingsGoals()
    }

    override suspend fun insertSavingsGoal(goal: SavingsGoalEntity) {
        savingsGoalDao.insertSavingsGoal(goal)
    }

    override suspend fun updateSavingsGoal(goal: SavingsGoalEntity) {
        savingsGoalDao.updateSavingsGoal(goal)
    }

    override suspend fun deleteSavingsGoal(goal: SavingsGoalEntity) {
        savingsGoalDao.deleteSavingsGoal(goal)
    }
}