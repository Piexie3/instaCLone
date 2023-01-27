package com.example.instaclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instaclone.feature_auth.presentation.AuthViewModel
import com.example.instaclone.feature_auth.presentation.HomeScreen
import com.example.instaclone.feature_auth.presentation.login.LoginScreen
import com.example.instaclone.feature_auth.presentation.signUp_sreen.SignUpScreen


@Composable
fun Navigation(
    viewModel: AuthViewModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignUpScreen.route
    ) {

        composable(route = Screens.LoginScreen.route) {
            LoginScreen(viewModel, navController )
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(viewModel, navController)

        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen()
        }
    }

}