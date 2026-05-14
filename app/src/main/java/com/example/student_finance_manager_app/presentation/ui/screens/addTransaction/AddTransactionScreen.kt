package com.example.student_finance_manager_app.presentation.ui.screens.addTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.components.FormField
import com.example.student_finance_manager_app.presentation.ui.screens.error.ErrorScreen
import com.example.student_finance_manager_app.presentation.ui.screens.loading.LoadingScreen
import com.example.student_finance_manager_app.presentation.viewmodel.add_transaction.AddTransactionNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.add_transaction.AddTransactionUiState
import com.example.student_finance_manager_app.presentation.viewmodel.add_transaction.AddTransactionViewModel

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var titleInput by rememberSaveable { mutableStateOf("") }
    var amountInput by rememberSaveable { mutableStateOf("") }
    var selectedType by rememberSaveable { mutableStateOf(TransactionType.EXPENSE) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                AddTransactionNavigationEvent.Navigate -> {}
                AddTransactionNavigationEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    when(uiState) {
        is AddTransactionUiState.Loading -> {
            LoadingScreen()
        }
        is AddTransactionUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as AddTransactionUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }
        else -> {
            AddTransactionScreen(
                titleInput = titleInput,
                amountInput = amountInput,
                selectedType = selectedType,
                error = null,
                onTitleChange = { titleInput = it },
                onAmountChange = { amountInput = it },
                onTypeChange = { selectedType = it },
                onSubmit = { viewModel.addTransaction(titleInput, amountInput, selectedType) },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun AddTransactionScreen(
    titleInput: String,
    amountInput: String,
    selectedType: TransactionType,
    error: String?,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .padding(top = dimensionResource(R.dimen.padding_large))
    ) {
        Text(
            text = stringResource(R.string.add_transaction_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        FormField(
            label = stringResource(R.string.label_naziv),
            value = titleInput,
            onValueChange = onTitleChange,
            isError = error != null,
            errorMessage = error
        )
        FormField(
            label = stringResource(R.string.label_iznos),
            value = amountInput,
            onValueChange = onAmountChange,
            isError = error != null,
            errorMessage = error
        )
        Text(
            text = stringResource(R.string.tip_transakcije),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Row {
            RadioButton(
                selected = selectedType == TransactionType.INCOME,
                onClick = { onTypeChange(TransactionType.INCOME) }
            )
            Text(
                text = stringResource(R.string.prihod),
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                )
            )
            RadioButton(
                selected = selectedType == TransactionType.EXPENSE,
                onClick = { onTypeChange(TransactionType.EXPENSE) }
            )
            Text(
                text = stringResource(R.string.rashod),
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                )
            )
        }
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth(),
            enabled = titleInput.isNotBlank() && amountInput.isNotBlank()
        ) {
            Text(text = stringResource(R.string.btn_dodaj))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTransactionScreenPreview() {
    MaterialTheme {
        AddTransactionScreen(
            titleInput = "",
            amountInput = "",
            selectedType = TransactionType.EXPENSE,
            error = null,
            onTitleChange = {},
            onAmountChange = {},
            onTypeChange = {},
            onSubmit = {}
        )
    }
}