package com.example.student_finance_manager_app.presentation.ui.screens.register

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
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    val errorImePrazno = stringResource(R.string.error_ime_prazno)
    val errorEmailPrazan = stringResource(R.string.error_email_prazan)
    val errorEmailIspravan = stringResource(R.string.error_email_ispravan)
    val errorLozinkaPrazna = stringResource(R.string.error_lozinka_prazna)
    val errorLozinkaKratka = stringResource(R.string.error_lozinka_kratka)
    val errorLozinkePodudaraju = stringResource(R.string.error_lozinke_podudaraju)

    RegisterScreen(
        name = name,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        nameError = nameError,
        emailError = emailError,
        passwordError = passwordError,
        confirmPasswordError = confirmPasswordError,
        onNameChange = { name = it; nameError = null },
        onEmailChange = { email = it; emailError = null },
        onPasswordChange = { password = it; passwordError = null },
        onConfirmPasswordChange = { confirmPassword = it; confirmPasswordError = null },
        onRegister = {
            nameError = null
            emailError = null
            passwordError = null
            confirmPasswordError = null
            when {
                name.isBlank() -> nameError = errorImePrazno
                email.isBlank() -> emailError = errorEmailPrazan
                !email.contains("@") -> emailError = errorEmailIspravan
                password.isBlank() -> passwordError = errorLozinkaPrazna
                password.length < 6 -> passwordError = errorLozinkaKratka
                password != confirmPassword -> confirmPasswordError = errorLozinkePodudaraju
                else -> onNavigateToLogin()
            }
        },
        onNavigateToLogin = onNavigateToLogin,
        modifier = modifier
    )


}

@Composable
private fun RegisterScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    nameError: String?,
    emailError: String?,
    passwordError: String?,
    confirmPasswordError: String?,
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
            isError = nameError != null,
            errorMessage = nameError
        )
        FormField(
            label = stringResource(R.string.label_email),
            value = email,
            onValueChange = onEmailChange,
            isError = emailError != null,
            errorMessage = emailError
        )
        FormField(
            label = stringResource(R.string.label_lozinka),
            value = password,
            onValueChange = onPasswordChange,
            isError = passwordError != null,
            errorMessage = passwordError
        )
        FormField(
            label = stringResource(R.string.label_potvrdi_lozinku),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isError = confirmPasswordError != null,
            errorMessage = confirmPasswordError
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
        RegisterScreen()
    }
}