package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.dao.BudgetDao
import com.example.student_finance_manager_app.model.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
) : BudgetRepository {

    override fun getAllBudgets(): Flow<List<BudgetEntity>> {
        return budgetDao.getAllBudgets()
    }

    override suspend fun insertBudget(budget: BudgetEntity) {
        budgetDao.insertBudget(budget)
    }

    override suspend fun updateBudget(budget: BudgetEntity) {
        budgetDao.updateTBudget(budget)
    }

    override suspend fun deleteBudget(budget: BudgetEntity) {
        budgetDao.deleteBudget(budget)
    }
}