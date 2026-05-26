package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionFirestoreRepository {
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transactionId: String)
}