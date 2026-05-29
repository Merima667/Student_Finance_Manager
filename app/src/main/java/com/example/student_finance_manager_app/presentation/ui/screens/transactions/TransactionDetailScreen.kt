package com.example.student_finance_manager_app.presentation.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.domain.data.HardcodedData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: String,
    transactionTitle: String,
    onNavigateBack: () -> Unit = {}
) {
    val transaction = HardcodedData.defaultTransactions.find {
        it.id.toString() == transactionId
    }

    TransactionDetailScreen(
        transactionTitle = transactionTitle,
        amount = transaction?.amount ?: 0.0,
        type = transaction?.type?.name ?: "",
        category = transaction?.category?.name ?: "",
        date = transaction?.date ?: "",
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionDetailScreen(
    transactionTitle: String,
    amount: Double,
    type: String,
    category: String,
    date: String,
    onNavigateBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(transactionTitle) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Nazad"
                        )
                }
            }
        )
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = transactionTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = "Iznos: $amount KM",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Tip: $type",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Kategorija: $category",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Datum: $date",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionDetailScreenPreview() {
    MaterialTheme {
        TransactionDetailScreen(
            transactionId = "1",
            transactionTitle = "Stipendija"
        )
    }
}