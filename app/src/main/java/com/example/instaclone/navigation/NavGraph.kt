package com.example.instaclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instaclone.feature_auth.presentation.login.LoginScreen
import com.example.instaclone.feature_auth.presentation.signup.SignUpScreen
import com.example.instaclone.feature_post.presentation.home.HomeScreen
import com.example.instaclone.feature_user.presentation.profile.ProfileScreen
import com.example.instaclone.feature_user.presentation.reels.ReelsScreen
import com.example.instaclone.feature_user.presentation.search.SearchScreen
import com.example.instaclone.feature_user.presentation.shopping.ShoppingScreen
import com.example.instaclone.feature_user.presentation.splash.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)

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
//            SplashScreen(navController)
//        }
    }

}
