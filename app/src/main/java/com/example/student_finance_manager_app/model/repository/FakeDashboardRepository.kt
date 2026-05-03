package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeDashboardRepository {
    suspend fun getDashboardData(shouldFail: Boolean = false): DashboardResult =
        withContext(Dispatchers.IO) {
            delay(2000)
            if (shouldFail) throw IllegalStateException("Failed to load dashboard.")

            val transactions = HardcodedData.defaultTransactions
            val name = HardcodedData.defaultUserProfile.name
            val totalIncome = transactions
                .filter { it.type == TransactionType.INCOME }
                .sumOf { it.amount }
            val totalExpanses = transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val balance = totalIncome - totalExpanses

            DashboardResult(
                name = name,
                totalIncome = totalIncome,
                totalExpanses = totalExpanses,
                balance = balance,
                transactions = transactions
            )
        }
}

data class DashboardResult(
    val name: String,
    val totalIncome: Double,
    val totalExpanses: Double,
    val balance: Double,
    val transactions: List<Transaction>
)