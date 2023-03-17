package com.example.instaclone.feature_user.presentation.profile.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R
import com.example.instaclone.ui.theme.instagramGradient

@Composable
fun RoundedCornerImage(
    image: String,
    modifier: Modifier = Modifier,
    onClick: ()-> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.placeholder)
            .build(),
        contentDescription = "Profile Image",
        modifier = modifier
            .padding(2.dp)
            .size(100.dp)
            .border(
                width = (1).dp,
                brush = Brush.linearGradient(
                    colors = instagramGradient,
                    tileMode = TileMode.Decal
                ),
                shape = CircleShape
            )
            .border(width = 2.dp, color = Color.White, shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
    )
}