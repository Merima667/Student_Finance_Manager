package com.example.student_finance_manager_app.presentation.navigation.bottom_bar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import com.example.student_finance_manager_app.presentation.navigation.Screen

object BottomBarNavigationItems {
    val items = listOf(
            BottomBarNavigationItem("Dashboard", Icons.Default.Home, Screen.Dashboard.route),
            BottomBarNavigationItem("Transakcije", Icons.Default.List, Screen.Transactions.route),
            BottomBarNavigationItem("Dodaj", Icons.Default.Add, Screen.AddTransaction.route),
            BottomBarNavigationItem("Budžet", Icons.Default.Star, Screen.Budget.route),
            BottomBarNavigationItem("Profil", Icons.Default.Person, Screen.Profile.route)
        )
}
