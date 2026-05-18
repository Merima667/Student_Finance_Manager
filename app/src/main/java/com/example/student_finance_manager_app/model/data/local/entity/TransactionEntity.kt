package com.example.student_finance_manager_app.model.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Long = 0,
    val title: String,
    val amount: Double,
    val type: String,
    val date: Long,
    @ColumnInfo(index = true)
    val categoryId: Long
)