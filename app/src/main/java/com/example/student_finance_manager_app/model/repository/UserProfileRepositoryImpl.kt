package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.data.local.dao.UserProfileDao
import com.example.student_finance_manager_app.model.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {

    override fun getUserProfile(): Flow<List<UserProfileEntity>> {
        return userProfileDao.getUserProfile()
    }

    override suspend fun insertUserProfile(userProfile: UserProfileEntity) {
        userProfileDao.insertUserProfile(userProfile)
    }

    override suspend fun updateUserProfile(userProfile: UserProfileEntity) {
        userProfileDao.updateUserProfile(userProfile)
    }

    override suspend fun deleteUserProfile(userProfile: UserProfileEntity) {
        userProfileDao.deleteUserProfile(userProfile)
    }
}