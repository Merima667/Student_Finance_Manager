package com.example.student_finance_manager_app.presentation.ui.screens.budget.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BudgetProgressBar(
    category: String,
    spent: Double,
    total: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "%.2f / %.2f KM".format(spent, total),
            style = MaterialTheme.typography.bodySmall
        )
        LinearProgressIndicator(
            progress = {(spent / total).toFloat().coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}