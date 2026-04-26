package com.example.student_finance_manager_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.student_finance_manager_app.presentation.navigation.NavGraph
import com.example.student_finance_manager_app.presentation.navigation.Screen
import com.example.student_finance_manager_app.presentation.navigation.Screen.Companion.getBottomNavRoutes
import com.example.student_finance_manager_app.presentation.navigation.BottomNavBar
import com.example.student_finance_manager_app.presentation.theme.Student_Finance_Manager_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Student_Finance_Manager_AppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController
                    .currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = currentRoute in getBottomNavRoutes()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if(showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}