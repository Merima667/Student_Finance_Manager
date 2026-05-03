package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeBudgetRepository {
    suspend fun getBudgetData(shouldFail: Boolean = false): BudgetResult =
        withContext(Dispatchers.IO) {
            delay(2000)
            if(shouldFail) throw IllegalStateException("Failed to load budget.")

            val transactions = HardcodedData.defaultTransactions
            val monthlyBudget = HardcodedData.defaultUserProfile.monthlyBudget

            val spentOnFood = transactions
                .filter { it.category == Category.FOOD && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnTransport = transactions
                .filter { it.category == Category.TRANSPORT && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnEducation = transactions
                .filter { it.category == Category.EDUCATION && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnEntertainment = transactions
                .filter { it.category == Category.ENTERTAINMENT && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnHealth = transactions
                .filter { it.category == Category.HEALTH && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val spentOnOther = transactions
                .filter { it.category == Category.OTHER && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }

            BudgetResult(
                monthlyBudget = monthlyBudget,
                spentOnFood = spentOnFood,
                spentOnTransport = spentOnTransport,
                spentOnEducation = spentOnEducation,
                spentOnEntertainment = spentOnEntertainment,
                spentOnHealth = spentOnHealth,
                spentOnOther = spentOnOther
            )
        }
}

data class BudgetResult(
    val monthlyBudget: Double,
    val spentOnFood: Double,
    val spentOnTransport: Double,
    val spentOnEducation: Double,
    val spentOnEntertainment: Double,
    val spentOnHealth: Double,
    val spentOnOther: Double
)