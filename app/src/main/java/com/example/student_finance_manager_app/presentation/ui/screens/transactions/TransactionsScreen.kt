package com.example.student_finance_manager_app.presentation.ui.screens.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.presentation.ui.screens.error.ErrorScreen
import com.example.student_finance_manager_app.presentation.ui.screens.loading.LoadingScreen
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.component.TransactionItem
import com.example.student_finance_manager_app.presentation.viewmodel.transaction.TransactionNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.transaction.TransactionUiState
import com.example.student_finance_manager_app.presentation.viewmodel.transaction.TransactionViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel,
    onTransactionClick: (Transaction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                TransactionNavigationEvent.Navigate -> {}
                TransactionNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is TransactionUiState.Loading -> {
            LoadingScreen()
        }
        is TransactionUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as TransactionUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }

        is TransactionUiState.Success -> {
            val data = (uiState as TransactionUiState.Success)
            TransactionScreen(
                transactions = data.transactions,
                searchQuery = data.searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onTransactionClick = onTransactionClick,
                onLoadFromNetwork = { viewModel.loadTransactionFromNetwork() },
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
private fun TransactionScreen(
    transactions: List<Transaction>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onTransactionClick: (Transaction) -> Unit,
    onLoadFromNetwork: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
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
            Button(
                onClick = onLoadFromNetwork,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Učitaj sa servera")
            }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Pretraži transakcije") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            if (transactions.isEmpty()) {
                Text(text = stringResource(R.string.empty_transactions))
            } else {
                LazyColumn (state = listState) {
                    items(transactions) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onClick = { onTransactionClick(transaction) }
                        )
                    }
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
        TransactionScreen(
            transactions = emptyList(),
            searchQuery = "",
            onSearchQueryChange = {},
            onLoadFromNetwork = {},
            onTransactionClick = {}
        )
    }
}