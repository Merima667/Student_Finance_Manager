package com.example.student_finance_manager_app.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.student_finance_manager_app.model.CategoryItem
import com.example.student_finance_manager_app.presentation.ui.components.CategoryCard
import com.example.student_finance_manager_app.presentation.ui.components.StatCard
import com.example.student_finance_manager_app.presentation.ui.screens.error.ErrorScreen
import com.example.student_finance_manager_app.presentation.ui.screens.loading.LoadingScreen
import com.example.student_finance_manager_app.presentation.ui.screens.profile.component.ProfileHeader
import com.example.student_finance_manager_app.presentation.viewmodel.profile.ProfileNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.profile.ProfileUiState
import com.example.student_finance_manager_app.presentation.viewmodel.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                ProfileNavigationEvent.Navigate -> {}
                ProfileNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is ProfileUiState.Loading -> {
            LoadingScreen()
        }
        is ProfileUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as ProfileUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }

        is ProfileUiState.Success -> {
            val data = (uiState as ProfileUiState.Success).profileData
            ProfileScreen(
                name = data.name,
                monthlyBudget = data.monthlyBudget,
                totalTransactions = data.totalTransactions,
                totalIncome = data.totalIncome,
                totalExpenses = data.totalExpenses,
                categories = data.categories,
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
private fun ProfileScreen(
    name: String,
    monthlyBudget: Double,
    totalTransactions: Double,
    totalIncome: Double,
    totalExpenses: Double,
    categories: List<CategoryItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
            items(categories) { category ->
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
        ProfileScreen(
            name = "Student",
            monthlyBudget = 1000.0,
            totalTransactions = 10.0,
            totalIncome = 800.0,
            totalExpenses = 300.0,
            categories = emptyList()
        )
    }
}