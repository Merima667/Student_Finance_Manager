package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<List<UserProfileEntity>>
    suspend fun insertUserProfile(userProfile: UserProfileEntity)
    suspend fun updateUserProfile(userProfile: UserProfileEntity)
    suspend fun deleteUserProfile(userProfile: UserProfileEntity)
}