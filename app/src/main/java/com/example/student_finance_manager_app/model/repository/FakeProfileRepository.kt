package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.CategoryItem
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.collections.filter

class FakeProfileRepository {
    suspend fun getProfileData(shouldFail: Boolean = false): ProfileResult =
        withContext(Dispatchers.IO) {
            delay(2000)
            if (shouldFail) throw IllegalStateException("Failed to load profile.")

            val name = HardcodedData.defaultUserProfile.name
            val monthlyBudget = HardcodedData.defaultUserProfile.monthlyBudget
            val transactions = HardcodedData.defaultTransactions
            val totalTransactions = transactions.size.toDouble()
            val totalIncome = transactions
                .filter { it.type == TransactionType.INCOME }
                .sumOf { it.amount }
            val totalExpenses = transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val categories = HardcodedData.defaultCategories

            ProfileResult(
                name = name,
                monthlyBudget = monthlyBudget,
                totalTransactions = totalTransactions,
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                categories = categories
            )
        }

    suspend fun getProfileCategories(): List<CategoryItem> =
        withContext(Dispatchers.IO) {
            delay(1500)
            HardcodedData.defaultCategories
        }

    suspend fun getProfileStats(): ProfileStats =
        withContext(Dispatchers.IO) {
            delay(1000)
            val transactions = HardcodedData.defaultTransactions
            ProfileStats(
                totalTransactions = transactions.size.toDouble(),
                totalIncome = transactions
                    .filter { it.type == TransactionType.INCOME }
                    .sumOf { it.amount },
                totalExpenses = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }
            )
        }
}

data class ProfileResult(
    val name: String,
    val monthlyBudget: Double,
    val totalTransactions: Double,
    val totalIncome: Double,
    val totalExpenses: Double,
    val categories: List<CategoryItem>
)

data class ProfileStats(
    val totalTransactions: Double,
    val totalIncome: Double,
    val totalExpenses: Double
)