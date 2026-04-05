package com.example.student_finance_manager_app.presentation.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.component.TransactionItem

@Composable
fun TransactionScreen(
    transactions: List<Transaction> = listOf(
        Transaction(title = "Stipendija", amount = 300.0, type = TransactionType.INCOME, category = Category.OTHER, date = "2026-03-01"),
        Transaction(title = "Rucak", amount = 8.50, type = TransactionType.EXPENSE, category = Category.FOOD, date = "2026-03-10"),
        Transaction(title = "Bus karta", amount = 2.0, type = TransactionType.EXPENSE, category = Category.TRANSPORT, date = "2026-03-11"),
        Transaction(title = "Udzbenik", amount = 35.0, type = TransactionType.EXPENSE, category = Category.EDUCATION, date = "2026-03-12")
    ),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.transactions_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        if (transactions.isEmpty()) {
            Text(text = stringResource(R.string.empty_transactions))
        } else {
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionScreenPreview() {
    MaterialTheme {
        TransactionScreen()
    }
}