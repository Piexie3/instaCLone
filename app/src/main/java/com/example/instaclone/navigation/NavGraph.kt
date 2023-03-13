package com.example.instaclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instaclone.feature_auth.presentation.AuthViewModel
import com.example.instaclone.feature_posts.presentation.home.HomeScreen
import com.example.instaclone.feature_auth.presentation.login.LoginScreen
import com.example.instaclone.feature_auth.presentation.signUp_sreen.SignUpScreen
import com.example.instaclone.feature_posts.presentation.profile.ProfileScreen
import com.example.instaclone.feature_posts.presentation.reels.ReelsScreen
import com.example.instaclone.feature_posts.presentation.search.SearchScreen
import com.example.instaclone.feature_posts.presentation.shopping.ShoppingScreen
import com.example.instaclone.feature_posts.presentation.splash.SplashScreen


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
            HomeScreen(navController)
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = Screens.ReelsScreen.route) {
            ReelsScreen(navController)
        }
        composable(route = Screens.ShoppingScreen.route) {
            ShoppingScreen(navController)
        }
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
//        composable(route = Screens.SplashScreen.route) {
//            SplashScreen(navController,viewModel)
//        }
    }

}