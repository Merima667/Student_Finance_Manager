package com.example.student_finance_manager_app.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.student_finance_manager_app.presentation.viewmodel.FinanceViewModel
import com.example.student_finance_manager_app.presentation.ui.screens.profile.component.ProfileHeader
import com.example.student_finance_manager_app.presentation.ui.components.StatCard

@Composable
fun ProfileScreen(viewModel: FinanceViewModel) {
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
            name = viewModel.userProfile.name,
            monthlyBudget = viewModel.userProfile.monthlyBudget
        )
        StatCard(title = stringResource(R.string.stat_ukupno_transakcija), amount = viewModel.transactions.size.toDouble())
        StatCard(title = stringResource(R.string.stat_prihodi), amount = viewModel.totalIncome)
        StatCard(title = stringResource(R.string.stat_rashodi), amount = viewModel.totalExpenses)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(viewModel = FinanceViewModel())
    }
}