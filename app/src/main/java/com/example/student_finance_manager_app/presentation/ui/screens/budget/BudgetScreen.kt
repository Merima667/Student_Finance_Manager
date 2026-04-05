package com.example.student_finance_manager_app.presentation.ui.screens.budget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.student_finance_manager_app.presentation.ui.screens.budget.component.BudgetProgressBar

@Composable
fun BudgetScreen(
    monthlyBudget: Double = 500.0,
    spentOnFood: Double = 8.50,
    spentOnTransport: Double = 2.0,
    spentOnEducation: Double = 35.0,
    spentOnEntertainment: Double = 0.0,
    spentOnHealth: Double = 0.0,
    spentOnOther: Double = 300.0,
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
        BudgetScreen()
    }
}