package com.example.instaclone.feature_user.presentation.profile

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R
import com.example.instaclone.feature_user.domain.models.TabModel
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.presentation.profile.composables.MyProfile
import com.example.instaclone.feature_user.presentation.profile.composables.PostSection
import com.example.instaclone.feature_user.presentation.profile.composables.StatSection
import com.example.instaclone.feature_user.presentation.profile.composables.TabView
import com.example.instaclone.navigation.BottomNavItem
import com.example.instaclone.navigation.BottomNavMenu
import com.example.instaclone.navigation.Screens

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
//    when(userViewModel.setUserData.value){
//        is Resource.Success->{
//
//        }
//        is Resource.Loading->{
//
//        }
//        is Resource.Error->{
//
//        }
//    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    isLoading = profileViewModel.isLoading.value
    var userDataFromFirebase by remember { mutableStateOf(User()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var email by remember {
        mutableStateOf("")
    }
    email = userDataFromFirebase.userEmail

    var name by remember { mutableStateOf("") }
    name = userDataFromFirebase.userName

    var surName by remember {
        mutableStateOf("")
    }
    surName = userDataFromFirebase.userSurName
    var bio by remember { mutableStateOf("") }
    bio = userDataFromFirebase.bio

    var phoneNumber by remember { mutableStateOf("") }
    phoneNumber = userDataFromFirebase.userPhoneNumber


    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val scaffoldState = rememberScaffoldState()
    val isUserSignOut = profileViewModel.isUserSignOutState.value
    LaunchedEffect(key1 = isUserSignOut) {
        if (isUserSignOut) {
            navController.popBackStack()
            navController.navigate(Screens.LoginScreen.route)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Monospace,
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        onClick = {
                            navController.navigate(Screens.CreatePostScreen.route)
                        }) {
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
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userDataFromFirebase.imageUrl)
                            .error(R.drawable.ic_broken_image)
                            .crossfade(200)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile Image"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    StatSection()
                }
            }

            MyProfile(
                displayName = surName,
                bio = bio,
                url = ""
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(190.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.ProfileUpdate.route) {
                            popUpTo(Screens.ProfileScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.45f)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Edit Profile"
                    )
                }
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
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> PostSection(
                    posts = listOf(
                        painterResource(id = R.drawable.post2),
                        painterResource(id = R.drawable.post),
                        painterResource(id = R.drawable.profile_image),
                        painterResource(id = R.drawable.profile_image),
                        painterResource(id = R.drawable.post),
                    )
                )
                1 -> {

                }
                2 -> {

                }
            }
        }
    }
}