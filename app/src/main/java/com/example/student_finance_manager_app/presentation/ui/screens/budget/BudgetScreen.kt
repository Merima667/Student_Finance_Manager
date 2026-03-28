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
import com.example.student_finance_manager_app.presentation.viewmodel.FinanceViewModel

@Composable
fun BudgetScreen(viewModel: FinanceViewModel) {
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
            text = stringResource(R.string.budget_monthly).format(viewModel.userProfile.monthlyBudget),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )

        val spentOnFood = viewModel.transactions
            .filter { it.category == Category.FOOD }.sumOf { it.amount }
        val spentOnTransport = viewModel.transactions
            .filter { it.category == Category.TRANSPORT }.sumOf { it.amount }
        val spentOnEducation = viewModel.transactions
            .filter { it.category == Category.EDUCATION }.sumOf { it.amount }
        val spentOnEntertainment = viewModel.transactions
            .filter { it.category == Category.ENTERTAINMENT }.sumOf { it.amount }
        val spentOnHealth = viewModel.transactions
            .filter { it.category == Category.HEALTH }.sumOf { it.amount }
        val spentOnOther = viewModel.transactions
            .filter { it.category == Category.OTHER }.sumOf { it.amount }

        LazyColumn {
            item {
                BudgetProgressBar(category = stringResource(R.string.category_hrana), spent = spentOnFood, total = viewModel.userProfile.monthlyBudget)
            }
            item {
                BudgetProgressBar(category = stringResource(R.string.category_transport), spent = spentOnTransport, total = viewModel.userProfile.monthlyBudget)
            }
            item {
                BudgetProgressBar(category = stringResource(R.string.category_obrazovanje), spent = spentOnEducation, total = viewModel.userProfile.monthlyBudget)
            }
            item {
                BudgetProgressBar(category = stringResource(R.string.category_zabava), spent = spentOnEntertainment, total = viewModel.userProfile.monthlyBudget)
            }
            item {
                BudgetProgressBar(category = stringResource(R.string.category_zdravlje), spent = spentOnHealth, total = viewModel.userProfile.monthlyBudget)
            }
            item {
                BudgetProgressBar(category = stringResource(R.string.category_ostalo), spent = spentOnOther, total = viewModel.userProfile.monthlyBudget)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BudgetScreenPreview() {
    MaterialTheme {
        BudgetScreen(viewModel = FinanceViewModel())
    }
}