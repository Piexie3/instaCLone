package com.example.instaclone.feature_post.presentation.home.composables


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.instaclone.ui.theme.Green40
import com.example.instaclone.ui.theme.instagramGradient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R



@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostCard(
    profileImage: String,
    postImage: String,
    userName: String,
    postDescription: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(.8f)
            ) {
                val viewed by remember {
                    mutableStateOf(false)
                }
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .border(
                            shape = CircleShape,
                            border = if (viewed) {
                                BorderStroke(
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
                            } else {
                                BorderStroke(
                                    width = 2.dp,
                                    color = Color.Gray
                                )
                            }
                        )
                        .padding(4.dp)
                        .clickable {

                        }) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profileImage)
                            .crossfade(durationMillis = 250)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ic_person),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        fallback = painterResource(id = R.drawable.ic_person),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp),
                        contentScale = ContentScale.Crop

                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = userName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = "@$userName",
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.ic_broken_image),
            fallback = painterResource(id = R.drawable.placeholder),
            contentDescription = "post image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
        )
        val state = rememberPagerState()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(.35f), horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "favourite",
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "favourite",
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "favourite",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = state,
                activeColor = Green40,
                inactiveColor = Color.Gray
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = "AddBookMark")
            }
        }
        Text(
            text = postDescription,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Divider()
    }
}
