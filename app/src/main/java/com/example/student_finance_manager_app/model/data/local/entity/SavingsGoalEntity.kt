package com.example.student_finance_manager_app.model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings_goals")
data class SavingsGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val goalId: Long = 0,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadline: Long
)