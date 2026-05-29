package com.example.student_finance_manager_app.model.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.student_finance_manager_app.model.datasource.local.dao.BudgetDao
import com.example.student_finance_manager_app.model.datasource.local.dao.CategoryDao
import com.example.student_finance_manager_app.model.datasource.local.dao.SavingsGoalDao
import com.example.student_finance_manager_app.model.datasource.local.dao.TransactionDao
import com.example.student_finance_manager_app.model.datasource.local.dao.UserProfileDao
import com.example.student_finance_manager_app.model.datasource.local.entity.BudgetEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.CategoryEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.SavingsGoalEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.TransactionEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.UserProfileEntity

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