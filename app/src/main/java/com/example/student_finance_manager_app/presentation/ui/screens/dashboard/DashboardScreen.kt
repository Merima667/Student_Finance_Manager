package com.example.student_finance_manager_app.presentation.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.presentation.ui.components.StatCard
import com.example.student_finance_manager_app.presentation.ui.components.TransactionCard
import com.example.student_finance_manager_app.presentation.ui.screens.error.ErrorScreen
import com.example.student_finance_manager_app.presentation.ui.screens.loading.LoadingScreen
import com.example.student_finance_manager_app.presentation.viewmodel.dashboard.DashboardNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.dashboard.DashboardUiState
import com.example.student_finance_manager_app.presentation.viewmodel.dashboard.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val financeTip by viewModel.financeTip.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                DashboardNavigationEvent.Navigate -> {}
                DashboardNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is DashboardUiState.Loading -> {
            LoadingScreen()
        }

        is DashboardUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as DashboardUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }

        is DashboardUiState.Success -> {
            val data = (uiState as DashboardUiState.Success).dashboardData
            DashboardScreen(
                name = data.name,
                totalIncome = data.totalIncome,
                totalExpenses = data.totalExpanses,
                balance = data.balance,
                transactions = data.transactions,
                financeTip = financeTip,
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
private fun DashboardScreen(
    name: String,
    totalIncome: Double,
    totalExpenses: Double,
    balance: Double,
    transactions: List<Transaction>,
    financeTip: String,
    modifier: Modifier = Modifier
) {
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

        if (financeTip.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tip: $financeTip",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        }

        Text(
            text = "Zadnje transakcije",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        LazyRow {
            items(transactions) { transaction ->
                TransactionCard(transaction = transaction)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(
            name = "Student",
            totalIncome = 1000.0,
            totalExpenses = 500.0,
            balance = 500.0,
            transactions = emptyList(),
            financeTip = "Track your daily expenses to stay on budget"
        )
    }
}