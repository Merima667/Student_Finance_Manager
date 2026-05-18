package com.example.student_finance_manager_app.presentation.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.student_finance_manager_app.presentation.viewmodel.auth.register.RegisterNavigationEvent
import com.example.student_finance_manager_app.presentation.viewmodel.auth.register.RegisterUiState
import com.example.student_finance_manager_app.presentation.viewmodel.auth.register.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigate: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                RegisterNavigationEvent.Navigate -> onNavigate()
                RegisterNavigationEvent.NavigateBack -> {}
            }
        }
    }

    when(uiState) {
        is RegisterUiState.Loading -> {
            CircularProgressIndicator()
        }
        is RegisterUiState.Error -> {
            RegisterScreen(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                error = (uiState as RegisterUiState.Error).message,
                onNameChange = { name = it; viewModel.resetUiState() },
                onEmailChange = { email = it; viewModel.resetUiState() },
                onPasswordChange = { password = it; viewModel.resetUiState() },
                onConfirmPasswordChange = { confirmPassword = it; viewModel.resetUiState() },
                onRegister = { viewModel.onRegisterClick(name,email, password) },
                onNavigateToLogin = onNavigateToLogin,
                modifier = modifier
            )
        }
        else -> {
            RegisterScreen(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                error = null,
                onNameChange = { name = it; },
                onEmailChange = { email = it; },
                onPasswordChange = { password = it; },
                onConfirmPasswordChange = { confirmPassword = it; },
                onRegister = { viewModel.onRegisterClick(name, email, password) },
                onNavigateToLogin = onNavigateToLogin,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun RegisterScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    error: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
        )
        FormField(
            label = stringResource(R.string.label_ime),
            value = name,
            onValueChange = onNameChange,
            isError = error != null,
            errorMessage = error
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
        FormField(
            label = stringResource(R.string.label_potvrdi_lozinku),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isError = error != null,
            errorMessage = error
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Button(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && email.isNotBlank() &&
                    password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            Text(text = stringResource(R.string.btn_register))
        }
        TextButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.imas_racun))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            name = "",
            email = "",
            password = "",
            confirmPassword = "",
            error = null,
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRegister = {},
            onNavigateToLogin = {}
        )
    }
}