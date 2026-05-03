package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeAddTransactionRepository {
    suspend fun saveTransaction(
        title: String,
        amount: Double,
        type: TransactionType
    ): Unit = withContext(Dispatchers.IO) {
        delay(1000)
    }
}