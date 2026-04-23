package com.example.student_finance_manager_app.presentation.ui.screens.transactions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType

@Composable
fun TransactionItem(
        transaction: Transaction,
        onClick: () -> Unit = {}
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column{
                Text(text = transaction.title)
                Text(text = "${transaction.category} • ${transaction.date}")
            }

            val isIncome = transaction.type == TransactionType.INCOME
            Text(
                text = if (isIncome) "+%.2f KM".format(transaction.amount)
                       else "-%.2f KM".format(transaction.amount),
                color = if (isIncome) Color(0xFF4CAF50)
                        else Color(0xFFF44336)
            )
        }
    }
}