package com.example.student_finance_manager_app.model.repository

import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): TransactionFirestoreRepository {
    private val collection = firestore.collection("transactions")

    override fun getTransactions(): Flow<List<Transaction>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if(error != null) {
                close(error)
                return@addSnapshotListener
            }
            val transactions = snapshot?.documents?.mapNotNull { doc ->
                try {
                    Transaction(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        amount = doc.getDouble("amount") ?: 0.0,
                        type = when (doc.getString("type")?.uppercase()) {
                            "INCOME" -> TransactionType.INCOME
                            else -> TransactionType.EXPENSE
                        },
                        category = when (doc.getString("category")?.uppercase()) {
                            "FOOD" -> Category.FOOD
                            "TRANSPORT" -> Category.TRANSPORT
                            "EDUCATION" -> Category.EDUCATION
                            "ENTERTAINMENT" -> Category.ENTERTAINMENT
                            "HEALTH" -> Category.HEALTH
                            else -> Category.OTHER
                        },
                        date = doc.getString("date") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            trySend(transactions)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val data = hashMapOf(
            "title" to transaction.title,
            "amount" to transaction.amount,
            "category" to transaction.category.name,
            "date" to transaction.date,
            "type" to transaction.type.name
        )
        collection.add(data).await()
    }

    override suspend fun deleteTransaction(transactionId: String) {
        collection.document(transactionId).delete().await()
    }
}