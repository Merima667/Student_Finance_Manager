package com.example.student_finance_manager_app.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType

@Composable
fun TransactionCard(transaction: Transaction) {
    val isIncome = transaction.type == TransactionType.INCOME
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(end = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (isIncome) "+%.2f KM".format(transaction.amount)
                       else "-%.2f KM".format(transaction.amount),
                color = if (isIncome) Color(0xFF4CAF50) else Color(0xFFF44336),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = transaction.date,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}