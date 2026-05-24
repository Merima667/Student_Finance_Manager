package com.example.student_finance_manager_app.model.datasource.network.dto

data class TransactionDto(
    val id: Int,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val type: String,
    val user_id: Int = 1
)

data class CreateTransactionDto(
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val type: String,
    val user_id: Int = 1
)

data class UpdateTransactionDto(
    val title: String? = null,
    val amount: Double? = null,
    val category: String? = null,
    val date: String? = null,
    val type: String? = null
)