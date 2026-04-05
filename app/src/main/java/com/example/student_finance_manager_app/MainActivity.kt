package com.example.student_finance_manager_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.student_finance_manager_app.presentation.ui.screens.login.LoginScreen
import com.example.student_finance_manager_app.presentation.theme.Student_Finance_Manager_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Student_Finance_Manager_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                    //RegisterScreen()
                    //DashboardScreen()
                    //AddTransactionScreen()
                    //ProfileScreen()
                    //BudgetScreen()
                    //TransactionScreen()
                }
            }
        }
    }
}