package com.example.student_finance_manager_app.model.repository.mappers

import com.example.student_finance_manager_app.model.UserProfile
import com.example.student_finance_manager_app.model.data.local.entity.UserProfileEntity

fun UserProfileEntity.toUserProfile(): UserProfile {
    return UserProfile(
        name = name,
        monthlyBudget = monthlyBudget
    )
}

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        userId = 0L,
        name = name,
        email = "",
        monthlyBudget= monthlyBudget
    )
}