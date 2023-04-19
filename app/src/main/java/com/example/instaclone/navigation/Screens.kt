package com.example.instaclone.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "LogIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object HomeScreen : Screens(route = "Home_Screen")
    object ProfileUpdate : Screens(route = "Profile_update_Screen")
    object SplashScreen : Screens(route = "Home_Screen")
    object ProfileScreen : Screens(route = "Profile_Screen")
    object ReelsScreen : Screens(route = "Reels_Screen")
    object SearchScreen : Screens(route = "Search_Screen")
    object ShoppingScreen : Screens(route = "Shopping_Screen")
    object StoryScreen : Screens(route = "Story_Screen")
    object AddPostScreen : Screens(route = "Add_post_Screen")
    object CreatePostScreen : Screens(route = "create_post_Screen")
}