package com.example.instaclone.feature_user.presentation.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.instaclone.R
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_user.domain.models.TabModel
import com.example.instaclone.feature_user.presentation.profile.composables.*
import com.example.instaclone.navigation.BottomNavItem
import com.example.instaclone.navigation.BottomNavMenu

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val userViewModel: UserViewModel = hiltViewModel()
    userViewModel.getUserInfo()
    val result = userViewModel.getUserData.value
    val obj = result.data
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    if (obj != null) {
                        Text(
                            text = obj.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Monospace,
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "create",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.dehaze),
                            contentDescription = "settings",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                backgroundColor = Color.Transparent

            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.wrapContentHeight(),
                backgroundColor = Color.Black,
                cutoutShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                BottomNavMenu(selectedItem = BottomNavItem.PROFILE, navController = navController)
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
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            bottom = 20.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                ) {
                    if (obj != null) {
                        RoundedCornerImage(
                            image = obj.imageUrl,
                        ){

                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    StatSection()
                }
            }
            if (obj != null) {
                MyProfile(
                    displayName = obj.name,
                    bio = obj.bio,
                    url = obj.url
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(190.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                ActionButton(
                    text = "Edit Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.45f)
                        .height(35.dp)
                        .clickable {

                        }
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            TabView(
                tabModels = listOf(
                    TabModel(
                        image = painterResource(id = R.drawable.ic_grid),
                        text = "Posts"
                    ),
                    TabModel(
                        image = painterResource(id = R.drawable.video),
                        text = "reels"
                    ),
                    TabModel(
                        image = painterResource(id = R.drawable.ic_igtv),
                        text = "igtv"
                    ),
                    TabModel(
                        image = painterResource(id = R.drawable.ic_person),
                        text = "Contacts share"
                    )
                )
            ){
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> PostSection(
                    posts = listOf(
                        painterResource(id = R.drawable.post2),
                        painterResource(id = R.drawable.post),
                        painterResource(id = R.drawable.img_2),
                        painterResource(id = R.drawable.img_2),
                        painterResource(id = R.drawable.profile_image),
                        painterResource(id = R.drawable.img_2),
                        painterResource(id = R.drawable.profile_image),
                        painterResource(id = R.drawable.img_2),
                        painterResource(id = R.drawable.post),
                    ),
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                1->{

                }
                2->{

                }
            }
        }
    }
}