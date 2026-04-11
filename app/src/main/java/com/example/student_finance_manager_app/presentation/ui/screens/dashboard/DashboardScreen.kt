package com.example.student_finance_manager_app.presentation.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.student_finance_manager_app.presentation.ui.components.TransactionCard
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.components.StatCard

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    val name = HardcodedData.defaultUserProfile.name
    val totalIncome = HardcodedData.defaultTransactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }
    val totalExpenses = HardcodedData.defaultTransactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val balance = totalIncome - totalExpenses

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.dashboard_greeting, name),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                StatCard(title = stringResource(R.string.stat_balans), amount = balance)
                StatCard(title = stringResource(R.string.stat_prihodi), amount = totalIncome)
                StatCard(title = stringResource(R.string.stat_rashodi), amount = totalExpenses)
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Text(
            text = "Zadnje transakcije",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        LazyRow {
            items(HardcodedData.defaultTransactions) { transaction ->
                TransactionCard(transaction = transaction)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}