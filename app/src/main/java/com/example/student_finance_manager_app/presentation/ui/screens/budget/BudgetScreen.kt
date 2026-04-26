package com.example.student_finance_manager_app.presentation.ui.screens.budget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.presentation.ui.screens.budget.component.BudgetProgressBar
import com.example.student_finance_manager_app.presentation.viewmodel.budget.BudgetNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.budget.BudgetUiState
import com.example.student_finance_manager_app.presentation.viewmodel.budget.BudgetViewModel

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                BudgetNavigationEvent.Navigate -> {}
                BudgetNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is BudgetUiState.Loading -> {
            CircularProgressIndicator()
        }
        is BudgetUiState.Error -> {
            Text(text = (uiState as BudgetUiState.Error).message)
        }
        is BudgetUiState.Success -> {
            val data = (uiState as BudgetUiState.Success).budgetData
            BudgetScreen(
                monthlyBudget = data.monthlyBudget,
                spentOnFood = data.spentOnFood,
                spentOnTransport = data.spentOnTransport,
                spentOnEducation = data.spentOnEducation,
                spentOnEntertainment = data.spentOnEntertainment,
                spentOnHealth = data.spentOnHealth,
                spentOnOther = data.spentOnOther,
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
private fun BudgetScreen(
    monthlyBudget: Double,
    spentOnFood: Double,
    spentOnTransport: Double,
    spentOnEducation: Double,
    spentOnEntertainment: Double,
    spentOnHealth: Double,
    spentOnOther: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.budget_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        Text(
            text = stringResource(R.string.budget_monthly).format(monthlyBudget),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        LazyColumn {
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_hrana),
                    spent = spentOnFood,
                    total = monthlyBudget)
            }
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_transport),
                    spent = spentOnTransport,
                    total = monthlyBudget)
            }
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_obrazovanje),
                    spent = spentOnEducation,
                    total = monthlyBudget)
            }
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_zabava),
                    spent = spentOnEntertainment,
                    total = monthlyBudget)
            }
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_zdravlje),
                    spent = spentOnHealth,
                    total = monthlyBudget)
            }
            item {
                BudgetProgressBar(
                    category = stringResource(R.string.category_ostalo),
                    spent = spentOnOther,
                    total = monthlyBudget)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BudgetScreenPreview() {
    MaterialTheme {
        BudgetScreen(
            monthlyBudget = 1000.0,
            spentOnFood = 200.0,
            spentOnTransport = 100.0,
            spentOnEducation = 150.0,
            spentOnEntertainment = 50.0,
            spentOnHealth = 80.0,
            spentOnOther = 30.0
        )
    }
}