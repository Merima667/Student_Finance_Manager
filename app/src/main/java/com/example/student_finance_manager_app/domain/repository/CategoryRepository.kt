package com.example.student_finance_manager_app.domain.repository

import com.example.student_finance_manager_app.model.datasource.local.entity.CategoryEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.CategoryWithTransactions
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>
    fun getCategoriesWithTransactions(): Flow<List<CategoryWithTransactions>>
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun updateCategory(category: CategoryEntity)
    suspend fun deleteCategory(category: CategoryEntity)
}