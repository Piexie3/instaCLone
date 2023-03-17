package com.example.instaclone.feature_user.presentation.splash


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instaclone.R
import com.example.instaclone.feature_auth.presentation.viewModel.AuthViewModel
import com.example.instaclone.navigation.Screens

@Composable
fun SplashScreen(
    navController: NavController,
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authValue = authViewModel.isUserAuthenticated
    val scale = remember {
        Animatable(0f)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.insta_logo),
            contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = .3f,
            animationSpec = tween(
                1500,
                3000,
                easing = {
                    OvershootInterpolator(6f).getInterpolation(it)
                }
            )
        )
        if (authValue) {
            navController.navigate(Screens.HomeScreen.route)
        } else {
            navController.navigate(Screens.LoginScreen.route)
        }
    }
}
