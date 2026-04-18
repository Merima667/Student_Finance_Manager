package com.example.student_finance_manager_app.presentation.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.student_finance_manager_app.R
import com.example.student_finance_manager_app.presentation.ui.components.FormField

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val errorEmailPrazan = stringResource(R.string.error_email_prazan)
    val errorEmailIspravan = stringResource(R.string.error_email_ispravan)
    val errorLozinkaPrazna = stringResource(R.string.error_lozinka_prazna)
    val errorLozinkaKratka = stringResource(R.string.error_lozinka_kratka)

    LoginScreen(
        email = email,
        password = password,
        error = error,
        onEmailChange = { email = it; error = null },
        onPasswordChange = { password = it; error = null },
        onLogin = {
            when{
                email.isBlank() -> error = errorEmailPrazan
                !email.contains("@") -> error = errorEmailIspravan
                password.isBlank() -> error = errorLozinkaPrazna
                password.length < 6 -> error = errorLozinkaKratka
                else -> {
                    error = null
                    onLoginSuccess()
                }
            }
        },
        onNavigateToRegister = onNavigateToRegister,
        modifier = modifier
    )
}

@Composable
private fun LoginScreen(
    email: String,
    password: String,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .padding(top = dimensionResource(R.dimen.padding_large))
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        FormField(
            label = stringResource(R.string.label_email),
            value = email,
            onValueChange = onEmailChange,
            isError = error != null,
            errorMessage = error
        )
        FormField(
            label = stringResource(R.string.label_lozinka),
            value = password,
            onValueChange = onPasswordChange,
            isError = error != null,
            errorMessage = error
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )
        }
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(text = stringResource(R.string.btn_login))
        }
        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.nemas_racun))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}