package com.example.student_finance_manager_app.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.student_finance_manager_app.model.data.local.dao.BudgetDao
import com.example.student_finance_manager_app.model.data.local.dao.CategoryDao
import com.example.student_finance_manager_app.model.data.local.dao.SavingsGoalDao
import com.example.student_finance_manager_app.model.data.local.dao.TransactionDao
import com.example.student_finance_manager_app.model.data.local.dao.UserProfileDao
import com.example.student_finance_manager_app.model.data.local.entity.BudgetEntity
import com.example.student_finance_manager_app.model.data.local.entity.CategoryEntity
import com.example.student_finance_manager_app.model.data.local.entity.SavingsGoalEntity
import com.example.student_finance_manager_app.model.data.local.entity.TransactionEntity
import com.example.student_finance_manager_app.model.data.local.entity.UserProfileEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class,
        UserProfileEntity::class,
        SavingsGoalEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun savingsGoalDao(): SavingsGoalDao
}