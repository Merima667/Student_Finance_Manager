package com.example.student_finance_manager_app.model.datasource.network.mapper

import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.datasource.network.dto.TransactionDto

fun TransactionDto.toTransactionModel(): Transaction {
    return Transaction(
        id = this.id.toString(),
        title = this.title,
        amount = this.amount,
        type = when (this.type.uppercase()) {
            "INCOME" -> TransactionType.INCOME
            else -> TransactionType.EXPENSE
        },
        category = when (this.category.uppercase()) {
            "FOOD" -> Category.FOOD
            "TRANSPORT" -> Category.TRANSPORT
            "EDUCATION" -> Category.EDUCATION
            "ENTERTAINMENT" -> Category.ENTERTAINMENT
            "HEALTH" -> Category.HEALTH
            else -> Category.OTHER
        },
        date = this.date
    )
}