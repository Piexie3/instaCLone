package com.example.instaclone.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "LogIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object HomeScreen : Screens(route = "Home_Screen")
    object SplashScreen : Screens(route = "Home_Screen")
    object ProfileScreen : Screens(route = "Profile_Screen")
    object ReelsScreen : Screens(route = "Reels_Screen")
    object SearchScreen : Screens(route = "Search_Screen")
    object ShoppingScreen : Screens(route = "Shopping_Screen")
}