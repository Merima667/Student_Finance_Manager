package com.example.student_finance_manager_app.presentation.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.component.TransactionItem
import com.example.student_finance_manager_app.presentation.viewmodel.FinanceViewModel

@Composable
fun TransactionScreen(viewModel: FinanceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.transactions_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        if (viewModel.transactions.isEmpty()) {
            Text(text = stringResource(R.string.empty_transactions))
        } else {
            LazyColumn {
                items(viewModel.transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionScreenPreview() {
    MaterialTheme {
        TransactionScreen(viewModel = FinanceViewModel())
    }
}