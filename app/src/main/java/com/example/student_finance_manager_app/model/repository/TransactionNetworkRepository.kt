package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.domain.data.Transaction
import com.example.student_finance_manager_app.domain.data.Category
import com.example.student_finance_manager_app.domain.data.TransactionType
import com.example.student_finance_manager_app.model.datasource.network.dto.CreateTransactionDto
import com.example.student_finance_manager_app.model.datasource.network.dto.TransactionDto
import com.example.student_finance_manager_app.model.datasource.network.dto.UpdateTransactionDto
import com.example.student_finance_manager_app.model.datasource.network.service.TransactionApiService
import javax.inject.Inject

class TransactionNetworkRepository @Inject constructor(
    private val apiService: TransactionApiService
) {
    private fun mapToTransaction(dto: TransactionDto): Transaction {
        return Transaction(
            id = dto.id.toString(),
            title = dto.title,
            amount = dto.amount,
            type = when (dto.type.uppercase()) {
                "INCOME" -> TransactionType.INCOME
                else -> TransactionType.EXPENSE
            },
            category = when (dto.category.uppercase()) {
                "FOOD" -> Category.FOOD
                "TRANSPORT" -> Category.TRANSPORT
                "EDUCATION" -> Category.EDUCATION
                "ENTERTAINMENT" -> Category.ENTERTAINMENT
                "HEALTH" -> Category.HEALTH
                else -> Category.OTHER
            },
            date = dto.date
        )
    }

    suspend fun getTransactions(): List<Transaction> {
        return apiService.getTransactions().map { mapToTransaction(it) }
    }

    suspend fun getTransactionById(id: Int): Transaction {
        return mapToTransaction(apiService.getTransactionById(id))
    }

    suspend fun createTransaction(
        title: String,
        amount: Double,
        category: String,
        date: String,
        type: String
    ): Transaction {
        val dto = CreateTransactionDto(
            title = title,
            amount = amount,
            category = category,
            date = date,
            type = type
        )
        return mapToTransaction(apiService.createTransaction(dto))
    }

    suspend fun updateTransaction(
        id: Int,
        title: String? = null,
        amount: Double? = null,
        category: String? = null,
        date: String? = null,
        type: String? = null
    ): Transaction {
        val dto = UpdateTransactionDto(
            title = title,
            amount = amount,
            category = category,
            date = date,
            type = type
        )
        return mapToTransaction(apiService.updateTransaction(id, dto))
    }

    suspend fun deleteTransaction(id: Int) {
        apiService.deleteTransaction(id)
    }
}