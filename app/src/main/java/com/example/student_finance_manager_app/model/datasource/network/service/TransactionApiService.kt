package com.example.student_finance_manager_app.model.datasource.network.service

import com.example.student_finance_manager_app.model.datasource.network.dto.CreateTransactionDto
import com.example.student_finance_manager_app.model.datasource.network.dto.TransactionDto
import com.example.student_finance_manager_app.model.datasource.network.dto.UpdateTransactionDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransactionApiService {
    @GET("transactions/")
    suspend fun getTransactions(
        @Header("X-Authentication") authHeader: String = "yes"
    ): List<TransactionDto>
    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    ): TransactionDto
    @POST ("transactions/")
    suspend fun createTransaction(
        @Body transaction: CreateTransactionDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): TransactionDto
    @PUT ("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body transaction: UpdateTransactionDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): TransactionDto
    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    )
}