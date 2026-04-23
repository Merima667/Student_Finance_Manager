package com.example.student_finance_manager_app.presentation.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login_screen")
    data object Register: Screen("register_screen")
    data object Dashboard: Screen("dashboard_screen")
    data object Transactions: Screen("transactions_screen")
    data object Budget: Screen("budget_screen")
    data object Profile: Screen("profile_screen")
    data object AddTransaction: Screen("add_transaction_screen")

    data object TransactionDetail : Screen("transaction_detail/{transactionId}/{transactionTitle}") {
        fun createRoute(transactionId: String, transactionTitle: String) =
            "transaction_detail/$transactionId/$transactionTitle"
    }
    companion object {
        fun getBottomNavRoutes(): List<String> {
            return listOf(
                Dashboard.route,
                Transactions.route,
                AddTransaction.route,
                Budget.route,
                Profile.route
            )
        }
    }
}