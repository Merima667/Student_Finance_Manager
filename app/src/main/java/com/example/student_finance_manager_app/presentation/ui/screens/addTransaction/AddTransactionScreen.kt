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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.presentation.ui.components.FormField

@Composable
fun AddTransactionScreen() {
    var titleInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var titleError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }

    val errorNaziv = stringResource(R.string.error_naziv)
    val errorIznosPrazan = stringResource(R.string.error_iznos_prazan)
    val errorIznosBroj = stringResource(R.string.error_iznos_broj)
    val errorIznosVeci = stringResource(R.string.error_iznos_veci)

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
            value = titleInput,
            onValueChange = { titleInput = it; titleError = null },
            isError = titleError != null,
            errorMessage = titleError
        )
        FormField(
            label = stringResource(R.string.label_iznos),
            value = amountInput,
            onValueChange = { amountInput = it; amountError = null },
            isError = amountError != null,
            errorMessage = amountError
        )
        Text(
            text = stringResource(R.string.tip_transakcije),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Row {
            RadioButton(
                selected = selectedType == TransactionType.INCOME,
                onClick = { selectedType = TransactionType.INCOME }
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
                onClick = { selectedType = TransactionType.EXPENSE }
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
            onClick = {
                titleError = null
                amountError = null
                when {
                    titleInput.isBlank() -> titleError = errorNaziv
                    amountInput.isBlank() -> amountError = errorIznosPrazan
                    amountInput.toDoubleOrNull() == null -> amountError = errorIznosBroj
                    amountInput.toDouble() <= 0 -> amountError = errorIznosVeci
                    else -> {
                        titleInput = ""
                        amountInput = ""
                        selectedType = TransactionType.EXPENSE
                    }
                }
            },
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
        AddTransactionScreen()
    }
}