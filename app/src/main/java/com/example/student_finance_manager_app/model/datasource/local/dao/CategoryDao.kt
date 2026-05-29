package com.example.student_finance_manager_app.model.datasource.local.dao

import androidx.room.*
import com.example.student_finance_manager_app.model.datasource.local.entity.CategoryEntity
import com.example.student_finance_manager_app.model.datasource.local.entity.CategoryWithTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateTCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithTransactions(): Flow<List<CategoryWithTransactions>>
}