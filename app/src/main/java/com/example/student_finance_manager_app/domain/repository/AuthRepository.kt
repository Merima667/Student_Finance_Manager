package com.example.student_finance_manager_app.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun register(email: String, password: String): FirebaseUser?
    suspend fun login(email: String, password: String): FirebaseUser?
    suspend fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggerIn(): Boolean
}