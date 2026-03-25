package com.example.student_finance_manager_app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student_finance_manager_app.presentation.viewmodel.FinanceViewModel

@Composable
fun DashboardScreen(viewModel: FinanceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Zdravo, ${viewModel.userProfile.name}!")
        Text(text = "Balans: ${viewModel.balance} KM")
        Text(text = "Prihodi: ${viewModel.totalIncome} KM")
        Text(text = "Rashodi: ${viewModel.totalExpenses} KM")
    }
}