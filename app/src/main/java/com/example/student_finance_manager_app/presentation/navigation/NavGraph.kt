package com.example.student_finance_manager_app.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import com.example.student_finance_manager_app.presentation.ui.screens.addTransaction.AddTransactionScreen
import com.example.student_finance_manager_app.presentation.ui.screens.budget.BudgetScreen
import com.example.student_finance_manager_app.presentation.ui.screens.dashboard.DashboardScreen
import com.example.student_finance_manager_app.presentation.ui.screens.login.LoginScreen
import com.example.student_finance_manager_app.presentation.ui.screens.profile.ProfileScreen
import com.example.student_finance_manager_app.presentation.ui.screens.register.RegisterScreen
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.TransactionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            println("NAVGRAPH LOGIN SE RENDERA")

            LoginScreen(
                onLoginSuccess = {
                    println("CLICK LOGIN")
                    navController.navigate(Screen.Dashboard.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.Transactions.route) {
            TransactionScreen()
        }
        composable(Screen.Budget.route) {
            BudgetScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen()
        }
    }
}