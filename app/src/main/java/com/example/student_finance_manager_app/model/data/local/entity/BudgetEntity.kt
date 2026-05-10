package com.example.student_finance_manager_app.model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val budgetId: Long = 0,
    val monthlyBudget: Double,
    val month: Int,
    val year: Int
)