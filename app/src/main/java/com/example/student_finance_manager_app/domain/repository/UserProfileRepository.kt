package com.example.student_finance_manager_app.domain.repository

import com.example.student_finance_manager_app.model.datasource.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<List<UserProfileEntity>>
    suspend fun insertUserProfile(userProfile: UserProfileEntity)
    suspend fun updateUserProfile(userProfile: UserProfileEntity)
    suspend fun deleteUserProfile(userProfile: UserProfileEntity)
}