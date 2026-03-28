package com.example.student_finance_manager_app.presentation.ui.screens.addTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.viewmodel.FinanceViewModel
import com.example.student_finance_manager_app.presentation.ui.components.FormField

@Composable
fun AddTransactionScreen(viewModel: FinanceViewModel) {
    Column(
        modifier = Modifier
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
            value = viewModel.titleInput,
            onValueChange = { viewModel.onTitleChange(it) },
            isError = viewModel.formError != null && viewModel.titleInput.isBlank(),
            errorMessage = if (viewModel.formError != null && viewModel.titleInput.isBlank())
                stringResource(R.string.error_naziv)
            else null
        )
        FormField(
            label = stringResource(R.string.label_iznos),
            value = viewModel.amountInput,
            onValueChange = { viewModel.onAmountChange(it) },
            isError = viewModel.formError != null && (
                    viewModel.amountInput.isBlank() ||
                            viewModel.amountInput.toDoubleOrNull() == null ||
                            (viewModel.amountInput.toDoubleOrNull() ?: 0.0) <= 0
                    ),
            errorMessage = if (viewModel.formError != null && viewModel.amountInput.isBlank())
                stringResource(R.string.error_iznos_prazan)
            else if (viewModel.formError != null && viewModel.amountInput.toDoubleOrNull() == null)
                stringResource(R.string.error_iznos_broj)
            else if (viewModel.formError != null && viewModel.amountInput.toDoubleOrNull() != null && viewModel.amountInput.toDouble() <= 0)
                stringResource(R.string.error_iznos_veci)
            else null
        )
        Text(
            text = stringResource(R.string.tip_transakcije),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Row {
            RadioButton(
                selected = viewModel.selectedType == TransactionType.INCOME,
                onClick = { viewModel.onTypeChange(TransactionType.INCOME) }
            )
            Text(
                text = stringResource(R.string.prihod),
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                )
            )
            RadioButton(
                selected = viewModel.selectedType == TransactionType.EXPENSE,
                onClick = { viewModel.onTypeChange(TransactionType.EXPENSE) }
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
            onClick = { viewModel.addTransaction() },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.titleInput.isNotBlank() && viewModel.amountInput.isNotBlank()
        ) {
            Text(text = stringResource(R.string.btn_dodaj))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTransactionScreenPreview() {
    MaterialTheme {
        AddTransactionScreen(viewModel = FinanceViewModel())
    }
}