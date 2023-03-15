package com.example.instaclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.instaclone.feature_user.presentation.profile.ProfileScreen
import com.example.instaclone.navigation.Navigation
import com.example.instaclone.ui.theme.InstaCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstaCloneTheme {
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                ) {
                    Navigation()
                }
            }
        }
    }
}
