package com.example.instaclone.feature_posts.presentation.splash


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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.instaclone.R
import com.example.instaclone.feature_auth.presentation.AuthViewModel
import com.example.instaclone.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authValue = authViewModel.isUserAuthenticated
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = .6f,
            animationSpec = tween(
                1500,
                easing = {
                    OvershootInterpolator(10f).getInterpolation(it)
                }
            )
        )
        delay(300)
        if (authValue){
            navController.navigate(Screens.HomeScreen.route)
        }
        else if (!authValue){
            navController.navigate(Screens.LoginScreen.route)

        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.instalogo),
            contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}