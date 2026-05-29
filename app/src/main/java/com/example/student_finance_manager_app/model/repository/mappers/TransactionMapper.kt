package com.example.student_finance_manager_app.model.repository.mappers

import com.example.student_finance_manager_app.domain.data.Category
import com.example.student_finance_manager_app.domain.data.Transaction
import com.example.student_finance_manager_app.domain.data.TransactionType
import com.example.student_finance_manager_app.model.datasource.local.entity.TransactionEntity

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = transactionId.toString(),
        title = title,
        amount = amount,
        type = TransactionType.valueOf(type),
        category = Category.OTHER,
        date = date.toString()
    )
}

fun Transaction.toEntity(categoryId: Long = 1L): TransactionEntity {
    return TransactionEntity(
        transactionId = 0L,
        title = title,
        amount = amount,
        type = type.name,
        date = System.currentTimeMillis(),
        categoryId = categoryId
    )
}