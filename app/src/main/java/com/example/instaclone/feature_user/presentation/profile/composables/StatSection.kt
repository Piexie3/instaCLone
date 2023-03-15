package com.example.instaclone.feature_user.presentation.profile.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {

        ProfileStat(numberText = "10", text = "Posts")
        ProfileStat(numberText = "118", text = "Followers")
        ProfileStat(numberText = "266", text = "Following")
    }
}

@Composable
fun ProfileStat(
    numberText: String, text: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = numberText, fontWeight = FontWeight.Bold, fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}