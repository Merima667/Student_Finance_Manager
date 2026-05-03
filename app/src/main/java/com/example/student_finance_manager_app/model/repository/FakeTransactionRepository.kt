package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay

class FakeTransactionRepository {
    suspend fun getTransactions(shouldFail: Boolean = false): List<Transaction> =
        withContext(Dispatchers.IO) {
            delay(2000)
            if(shouldFail) throw IllegalStateException("Failed to load transactions.")
            HardcodedData.defaultTransactions
        }
}