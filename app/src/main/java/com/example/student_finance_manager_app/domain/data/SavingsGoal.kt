package com.example.student_finance_manager_app.domain.data

data class SavingsGoal(
    val goalId: Long = 0,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadline: Long
)