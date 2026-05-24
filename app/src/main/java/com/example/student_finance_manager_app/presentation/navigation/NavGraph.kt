package com.example.student_finance_manager_app.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.TransactionDetailScreen
import com.example.student_finance_manager_app.presentation.ui.screens.addTransaction.AddTransactionScreen
import com.example.student_finance_manager_app.presentation.ui.screens.budget.BudgetScreen
import com.example.student_finance_manager_app.presentation.ui.screens.dashboard.DashboardScreen
import com.example.student_finance_manager_app.presentation.ui.screens.login.LoginScreen
import com.example.student_finance_manager_app.presentation.ui.screens.profile.ProfileScreen
import com.example.student_finance_manager_app.presentation.ui.screens.register.RegisterScreen
import com.example.student_finance_manager_app.presentation.ui.screens.transactions.TransactionScreen
import androidx.hilt.navigation.compose.hiltViewModel

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
            LoginScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navController.navigate(Screen.Dashboard.route) },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navController.navigate(Screen.Dashboard.route) },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(viewModel = hiltViewModel())
        }
        composable(Screen.Transactions.route) {
            TransactionScreen(
                viewModel = hiltViewModel(),
                onTransactionClick = { transaction ->
                    navController.navigate(
                        Screen.TransactionDetail.createRoute(
                            transactionId = transaction.id.toString(),
                            transactionTitle = transaction.title
                        )
                    )
                }
            )
        }
        composable(
            route = Screen.TransactionDetail.route,
            arguments = listOf(
                navArgument("transactionId") { type = NavType.StringType },
                navArgument("transactionTitle") { type = NavType.StringType }
            )
        ) {
            backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: ""
            val transactionTitle = backStackEntry.arguments?.getString("transactionTitle") ?: ""
            TransactionDetailScreen(
                transactionId = transactionId,
                transactionTitle = transactionTitle,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(Screen.Budget.route) {
            BudgetScreen(viewModel = hiltViewModel())
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}