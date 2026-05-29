package com.example.student_finance_manager_app.domain.repository

import com.example.student_finance_manager_app.domain.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionFirestoreRepository {
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transactionId: String)
}