package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.domain.data.SavingsGoal
import com.example.student_finance_manager_app.domain.repository.SavingsGoalRepository
import com.example.student_finance_manager_app.model.datasource.local.dao.SavingsGoalDao
import com.example.student_finance_manager_app.model.datasource.local.entity.SavingsGoalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavingsGoalRepositoryImpl @Inject constructor(
    private val savingsGoalDao: SavingsGoalDao
) : SavingsGoalRepository {

    override fun getAllSavingsGoals(): Flow<List<SavingsGoal>> {
        return savingsGoalDao.getAllSavingsGoals().map { entities ->
            entities.map { it.toSavingsGoal() }
        }
    }

    override suspend fun insertSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.insertSavingsGoal(goal.toEntity())
    }

    override suspend fun updateSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.updateSavingsGoal(goal.toEntity())
    }

    override suspend fun deleteSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.deleteSavingsGoal(goal.toEntity())
    }

    private fun SavingsGoalEntity.toSavingsGoal() = SavingsGoal(
        goalId = goalId,
        title = title,
        targetAmount = targetAmount,
        currentAmount = currentAmount,
        deadline = deadline
    )

    private fun SavingsGoal.toEntity() = SavingsGoalEntity(
        goalId = goalId,
        title = title,
        targetAmount = targetAmount,
        currentAmount = currentAmount,
        deadline = deadline
    )
}