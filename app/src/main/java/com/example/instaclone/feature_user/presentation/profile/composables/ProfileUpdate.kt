package com.example.instaclone.feature_user.presentation.profile.composables

import android.net.Uri
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.models.UserStatus
import com.example.instaclone.feature_user.presentation.profile.ProfileViewModel
import com.example.instaclone.navigation.Screens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileUpdate(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    keyboardController: SoftwareKeyboardController
) {

    val toastMessage = profileViewModel.toastMessage.value
//    LaunchedEffect(key1 = toastMessage) {
//        if (toastMessage != "") {
//            SnackbarController(this).showSnackbar(snackbarHostState, toastMessage, "Close")
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

    var userDataPictureUrl by remember { mutableStateOf("") }
    userDataPictureUrl = userDataFromFirebase.imageUrl

    val scrollState = rememberScrollState()

    var updatedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val isUserSignOut = profileViewModel.isUserSignOutState.value
    LaunchedEffect(key1 = isUserSignOut) {
        if (isUserSignOut) {
            navController.popBackStack()
            navController.navigate(Screens.LoginScreen.route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight().imePadding()
    ) {
        Surface(
            modifier = Modifier
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        ChooseProfilePicFromGallery(userDataPictureUrl) {
                            if (it != null) {
                                profileViewModel.uploadPictureToFirebase(it)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = email, style = MaterialTheme.typography.subtitle2)
                    ProfileTextField(
                        entry = name,
                        hint = "Full Name",
                        onChange = { name = it },
                    )
                    ProfileTextField(surName, "Surname", { surName = it })
                    ProfileTextField(bio, "About You", { bio = it })
                    ProfileTextField(
                        phoneNumber, "Phone Number", { phoneNumber = it },
                        keyboardType = KeyboardType.Phone
                    )
                    Button(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (name != "") {
                                profileViewModel.updateProfileToFirebase(User(userName = name))
                            }
                            if (surName != "") {
                                profileViewModel.updateProfileToFirebase(User(userSurName = surName))
                            }
                            if (bio != "") {
                                profileViewModel.updateProfileToFirebase(User(bio = bio))
                            }
                            if (phoneNumber != "") {
                                profileViewModel.updateProfileToFirebase(User(userPhoneNumber = phoneNumber))
                            }
                        },
                        enabled = updatedImage != null || name != "" || surName != "" || bio != "" || phoneNumber != ""
                    ) {
                        Text(text = "Save Profile", style = MaterialTheme.typography.subtitle1)
                        navController.navigate(Screens.ProfileScreen.route)
                    }
                    LogOutCustomText {
                        profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
                    }
                    Spacer(modifier = Modifier.height(50.dp))

                }
            }
        }
    }
}

@Composable
fun ProfileTextField(
    entry: String,
    hint: String,
    onChange: (String) -> Unit = {},
//    onFocusChange: (Boolean) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isNameChange by remember {
        mutableStateOf(false)
    }
    var isFocusChange by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("")
    }
    text = entry

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
//            .onFocusChanged {
//                if (isNameChange) {
////                    isFocusChange = true
////                    onFocusChange(isFocusChange)
//                }
//            },
        label = { Text(text = hint) },
        value = text,
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun LogOutCustomText(
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
    ) {
        Text(
            text = "Log Out",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onError
        )
    }
}