package com.example.instaclone.core.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.instaclone.ui.theme.instagramGradient
import com.example.instaclone.R


@Composable
fun CircularImage() {
    var viewed by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(
                shape = CircleShape,
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = instagramGradient,
                        start = Offset(
                            0f, 0f
                        ),
                        end = Offset(
                            100f, 100f
                        )
                    )
                )
            )
            .padding(4.dp)
            .clickable {

            }
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.background
            ),
            contentDescription = null,
            modifier= Modifier.clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }

}

