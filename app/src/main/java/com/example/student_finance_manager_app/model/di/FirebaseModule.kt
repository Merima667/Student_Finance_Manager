package com.example.student_finance_manager_app.model.di

import com.example.student_finance_manager_app.domain.repository.AuthRepository
import com.example.student_finance_manager_app.model.repository.AuthRepositoryImpl
import com.example.student_finance_manager_app.domain.repository.TransactionFirestoreRepository
import com.example.student_finance_manager_app.model.repository.TransactionFirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideTransactionFirestoreRepository(
        firestore: FirebaseFirestore
    ): TransactionFirestoreRepository {
        return TransactionFirestoreRepositoryImpl(firestore)
    }
}