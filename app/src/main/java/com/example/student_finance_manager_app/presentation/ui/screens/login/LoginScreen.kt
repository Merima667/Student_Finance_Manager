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
import com.example.student_finance_manager_app.presentation.ui.components.FormField
import com.example.student_finance_manager_app.presentation.ui.screens.loading.LoadingScreen
import com.example.student_finance_manager_app.presentation.viewmodel.auth.login.LoginNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.auth.login.LoginUiState
import com.example.student_finance_manager_app.presentation.viewmodel.auth.login.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigate: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                LoginNavigationEvent.Navigate -> onNavigate()
                LoginNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is LoginUiState.Loading -> {
            LoadingScreen()
        }
        is LoginUiState.Error -> {
            LoginScreen(
                email = email,
                password = password,
                error = (uiState as LoginUiState.Error).message,
                onEmailChange = { email = it; viewModel.resetUiState() },
                onPasswordChange = { password = it; viewModel.resetUiState() },
                onLogin = { viewModel.onLoginClick(email, password) },
                onNavigateToRegister = onNavigateToRegister,
                modifier = modifier
            )
        }
        else -> {
            LoginScreen(
                email = email,
                password = password,
                error = null,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLogin = { viewModel.onLoginClick(email, password) },
                onNavigateToRegister = onNavigateToRegister,
                modifier = modifier
            )
        }
    }
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
        LoginScreen(
            email = "",
            password = "",
            error = null,
            onEmailChange = {},
            onPasswordChange = {},
            onLogin = {},
            onNavigateToRegister = {}
        )
    }
}