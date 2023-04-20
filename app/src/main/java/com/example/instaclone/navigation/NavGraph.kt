package com.example.instaclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instaclone.feature_auth.presentation.login.LoginScreen
import com.example.instaclone.feature_auth.presentation.signup.SignUpScreen
import com.example.instaclone.feature_post.presentation.home.HomeScreen
import com.example.instaclone.feature_post.presentation.home.composables.stories.StoryScreen
import com.example.instaclone.feature_post.presentation.post.CreatePost
import com.example.instaclone.feature_post.presentation.post.CreatePostScreen
import com.example.instaclone.feature_post.presentation.post.PostViewModel
import com.example.instaclone.feature_user.presentation.profile.AddPostScreen
import com.example.instaclone.feature_user.presentation.profile.ProfileScreen
import com.example.instaclone.feature_user.presentation.profile.ProfileViewModel
import com.example.instaclone.feature_user.presentation.profile.composables.ProfileUpdate
import com.example.instaclone.feature_user.presentation.reels.ReelsScreen
import com.example.instaclone.feature_user.presentation.search.SearchScreen
import com.example.instaclone.feature_user.presentation.shopping.ShoppingScreen


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    postViewModel: PostViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
        composable(route = Screens.ProfileUpdate.route) {

            if (keyboardController != null) {
                ProfileUpdate(profileViewModel, navController, keyboardController)
            }
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController,postViewModel)
        }
        composable(route = Screens.CreatePostScreen.route) {
            CreatePostScreen(postViewModel,navController,profileViewModel)
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
            ProfileScreen(navController, profileViewModel)
        }
        composable(route = Screens.StoryScreen.route) {
            StoryScreen(navController)
        }
        composable(route = Screens.AddPostScreen.route) {
            AddPostScreen()
        }
        composable(route = Screens.CreatePost.route) {
            if (keyboardController != null) {
                CreatePost(postViewModel,navController,keyboardController)
            }
        }

    }

}
