package com.example.instaclone.feature_posts.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.instaclone.navigation.BottomNavItem
import com.example.instaclone.navigation.BottomNavMenu

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Scaffold(

    ) { padding ->
        Column(
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Profile Screen")
        }
    }
}