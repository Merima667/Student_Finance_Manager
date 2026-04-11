package com.example.student_finance_manager_app.presentation.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.student_finance_manager_app.model.HardcodedData
import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.component.TransactionItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

@Composable
fun TransactionScreen(
    transactions: List<Transaction> = HardcodedData.defaultTransactions,
    modifier: Modifier = Modifier
) {
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val filteredTransactions = transactions.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }
    Box(modifier = Modifier.fillMaxSize()) {
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pretraži transakcije") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            if (filteredTransactions.isEmpty()) {
                Text(text = stringResource(R.string.empty_transactions))
            } else {
                LazyColumn (state = listState) {
                    items(filteredTransactions) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onClick = { selectedTransaction = transaction }
                        )
                    }
                }
                selectedTransaction?.let { transaction ->
                    Text(
                        text = "Odabrano: ${transaction.title}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
        }
        if (showScrollToTop) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Idi na vrh"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionScreenPreview() {
    MaterialTheme {
        TransactionScreen()
    }
}