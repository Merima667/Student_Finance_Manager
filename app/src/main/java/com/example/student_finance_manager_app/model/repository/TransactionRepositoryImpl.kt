package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.domain.repository.TransactionRepository
import com.example.student_finance_manager_app.domain.data.Transaction
import com.example.student_finance_manager_app.model.datasource.local.dao.TransactionDao
import com.example.student_finance_manager_app.model.repository.mappers.toEntity
import com.example.student_finance_manager_app.model.repository.mappers.toTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toTransaction() }
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }
}