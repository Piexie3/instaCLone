package com.example.instaclone.feature_post.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instaclone.R
import com.example.instaclone.core.composables.CircularImage
import com.example.instaclone.feature_post.presentation.home.composables.PostCard
import com.example.instaclone.feature_user.presentation.profile.UserViewModel
import com.example.instaclone.navigation.BottomNavItem
import com.example.instaclone.navigation.BottomNavMenu
import com.example.instaclone.navigation.Screens
import com.example.instaclone.ui.theme.lighBlue
import kotlin.random.Random

@Composable
fun HomeScreen(
    navController: NavController
) {
    val userViewModel: UserViewModel = hiltViewModel()
    val result = userViewModel.getUserData.value
    val obj = result.data
    val link2 =
        "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1"
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.clickable {

                        },
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Instagram",
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Cursive,
                            style = MaterialTheme.typography.h4,
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                backgroundColor = Color.Transparent
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Transparent,
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
            LazyColumn() {
                item{
                    Stories(userImage = link2, userName = "Bett"){
                        navController.navigate(Screens.StoryScreen.route)
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(100) {

                    PostCard(
                        profileImage = link2,
                        postImage = "https://picsum.photos/seed/${Random.nextInt()}/300/200"
                    )

                }
            }
        }
    }
}

@Composable
fun Stories(
    userImage: String,
    userName: String,
    navController: () -> Unit
) {
    Spacer(modifier = Modifier.width(5.dp))
    LazyRow(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        items(100) {
            if (it == 0) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {

                        CircularImage(
                            onClicked = { /*TODO*/ },
                            userImage = userImage,
                            modifier = Modifier.size(70.dp)
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colors.background,
                                    shape = CircleShape
                                )
                                .align(
                                    Alignment.BottomEnd
                                )
                                .size(23.dp)
                        ) {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .background(lighBlue, CircleShape)
                                    .clip(
                                        CircleShape
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "add stories"
                                )
                            }
                        }

                    }
                    Text(
                        text = "your story"
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentSize()
                ) {
                    CircularImage(
                        onClicked = {
                                    navController()
                                    },
                        userImage = "https://picsum.photos/seed/${Random.nextInt()}/300/200",
                        modifier = Modifier.size(70.dp)
                    )
                    Text(text = userName)
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
    Divider()
}



