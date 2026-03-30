package com.example.student_finance_manager_app.model

import java.util.UUID

enum class TransactionType {
    INCOME, EXPENSE
}

enum class Category {
    FOOD, TRANSPORT, ENTERTAINMENT, EDUCATION, HEALTH, OTHER
}

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category,
    val date: String
)