package com.example.instaclone.feature_post.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R
import com.example.instaclone.core.composables.CircularImage
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.presentation.post.PostViewModel
import com.example.instaclone.navigation.BottomNavItem
import com.example.instaclone.navigation.BottomNavMenu
import com.example.instaclone.ui.theme.Green40
import com.example.instaclone.ui.theme.instagramGradient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun HomeScreen(
    navController: NavController
) {
    val postViewModel: PostViewModel = hiltViewModel()
    when(val result = postViewModel.postData.value){
        is Resource.Loading ->{
            CircularProgressIndicator()
        }
        is Resource.Success -> {
            val obj = result.data
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.clickable {

                                }
                            ) {
                                Text(
                                    text = "Instagram",
                                    fontFamily = FontFamily.Cursive,
                                    fontWeight = FontWeight.ExtraBold,
                                    style = MaterialTheme.typography.h5,
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.message),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = Color.Transparent,
                        cutoutShape = CircleShape
                    ) {
                        BottomNavMenu(selectedItem = BottomNavItem.HOME, navController)
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                        .padding(top = 5.dp)
                ) {
                    Stories()
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn() {
                        items(10) {
                            PostCard()
                        }
                    }
                }
            }

        }
        is Resource.Error -> {
            Toast.makeText(LocalContext.current, result.message,Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Stories() {
    LazyRow() {
        items(10) {
            CircularImage(
                onClicked = {

                },
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
    Divider()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostCard() {
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
                        .size(40.dp)
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
                            .data(R.drawable.profile_image)
                            .crossfade(durationMillis = 250)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ic_person),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        fallback = painterResource(id = R.drawable.ic_person),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "name",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = "@Username",
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
                .data(R.drawable.post)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.ic_broken_image),
            fallback = painterResource(id = R.drawable.placeholder),
            contentDescription = "post image",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        val state = rememberPagerState()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(.45f), horizontalArrangement = Arrangement.SpaceAround
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
                        painter = painterResource(id = R.drawable.comment),
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
        Spacer(modifier = Modifier.height(2.dp))
        Divider()
    }
}

@Composable
fun ListOfPosts() {

}
