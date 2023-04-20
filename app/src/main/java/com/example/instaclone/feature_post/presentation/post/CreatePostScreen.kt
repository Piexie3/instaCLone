package com.example.instaclone.feature_post.presentation.post

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instaclone.core.composables.TransparentHintTextField
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.presentation.profile.ProfileViewModel
import com.example.instaclone.feature_user.presentation.profile.composables.ChooseProfilePicFromGallery
import com.example.instaclone.navigation.Screens

@Composable
fun CreatePostScreen(
    postViewModel: PostViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    var postDescription by remember { mutableStateOf("") }

    var userDataFromFirebase by remember { mutableStateOf(User()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    val postImageUrl by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember {
            mutableStateOf("")
        }
        TransparentHintTextField(
            text = text,
            hint = "Post description",
            onValueChange = {
                text = it
            },
            onFocusChange = {
                it.isFocused
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        ChooseProfilePicFromGallery(
            postImageUrl
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            onClick = {
                postViewModel.createPostToFirebase(
                    Post(
                        postDescription = postDescription,
                        postImage = postImageUrl,
                        userName = userDataFromFirebase.userSurName,
                        userImage = userDataFromFirebase.imageUrl
                    )
                )
                navController.popBackStack()
                navController.navigate(Screens.HomeScreen.route)
            },
        ) {
            Text(text = "post", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun DescriptionText(
    hint: String,
    onChange: (String) -> Unit = {},
) {

}