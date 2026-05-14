package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.dao.CategoryDao
import com.example.student_finance_manager_app.model.data.local.entity.CategoryEntity
import com.example.student_finance_manager_app.model.data.local.entity.CategoryWithTransactions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    override fun getCategoriesWithTransactions(): Flow<List<CategoryWithTransactions>> {
        return categoryDao.getCategoriesWithTransactions()
    }

    override suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateTCategory(category)
    }

    override suspend fun deleteCategory(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }
}