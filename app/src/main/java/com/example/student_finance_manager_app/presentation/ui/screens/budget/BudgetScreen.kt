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
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.screens.budget.component.BudgetProgressBar

@Composable
fun BudgetScreen(

    modifier: Modifier = Modifier
) {
    val monthlyBudget = HardcodedData.defaultUserProfile.monthlyBudget
    val spentOnFood = HardcodedData.defaultTransactions
        .filter { it.category == Category.FOOD && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val spentOnTransport = HardcodedData.defaultTransactions
        .filter { it.category == Category.TRANSPORT && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val spentOnEducation = HardcodedData.defaultTransactions
        .filter { it.category == Category.EDUCATION && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val spentOnEntertainment = HardcodedData.defaultTransactions
        .filter { it.category == Category.ENTERTAINMENT && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val spentOnHealth = HardcodedData.defaultTransactions
        .filter { it.category == Category.HEALTH && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val spentOnOther = HardcodedData.defaultTransactions
        .filter { it.category == Category.OTHER && it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }

    BudgetScreen(
        monthlyBudget = monthlyBudget,
        spentOnFood = spentOnFood,
        spentOnTransport = spentOnTransport,
        spentOnEducation = spentOnEducation,
        spentOnEntertainment = spentOnEntertainment,
        spentOnHealth = spentOnHealth,
        spentOnOther = spentOnOther,
        modifier = modifier
    )
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
        BudgetScreen()
    }
}