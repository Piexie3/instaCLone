package com.example.instaclone.feature_user.presentation.profile.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R

@Composable
fun RoundedCornerImage(
    image: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.placeholder)
            .build(),
        contentDescription = "Profile Image",
        modifier = modifier.aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}