package com.example.student_finance_manager_app.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.presentation.ui.components.CategoryCard
import com.example.student_finance_manager_app.model.CategoryItem
import com.example.student_finance_manager_app.presentation.ui.screens.profile.component.ProfileHeader
import com.example.student_finance_manager_app.presentation.ui.components.StatCard

@Composable
fun ProfileScreen(
    name: String = HardcodedData.defaultUserProfile.name,
    monthlyBudget: Double = HardcodedData.defaultUserProfile.monthlyBudget,
    totalTransactions: Double = HardcodedData.defaultTransactions.size.toDouble(),
    totalIncome: Double = HardcodedData.defaultTransactions
        .filter { it.type.name == "INCOME" }
        .sumOf { it.amount },
    totalExpenses: Double = HardcodedData.defaultTransactions
        .filter { it.type.name == "EXPENSE" }
        .sumOf { it.amount },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        ProfileHeader(
            name = name,
            monthlyBudget = monthlyBudget
        )
        StatCard(
            title = stringResource(R.string.stat_ukupno_transakcija),
            amount = totalTransactions)
        StatCard(
            title = stringResource(R.string.stat_prihodi),
            amount = totalIncome)
        StatCard(
            title = stringResource(R.string.stat_rashodi),
            amount = totalExpenses)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Text(
            text = "Troškovi po kategoriji",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        LazyRow {
            items(HardcodedData.defaultCategories) { category ->
                CategoryCard(
                    categoryName = category.name,
                    amount = category.amount
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}