package com.example.instaclone.feature_auth.presentation.signUp_sreen

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.R
import com.example.instaclone.feature_auth.presentation.AuthViewModel
import com.example.instaclone.navigation.Screens
import com.example.instaclone.ui.theme.Violet30


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun SignUpScreen(
    navController: NavController,
) {
    val viewModel: AuthViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Top) {
                Text(text = "Getting Started", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Create an account to continue with your Instagram",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) {
        var userName by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        val isEmailValid by remember {
            derivedStateOf {
                Patterns.EMAIL_ADDRESS.matcher(email).matches()

            }
        }
        val isUserNameValid by remember {
            derivedStateOf {
                userName.length > 1
            }
        }
        val isPasswordValid by remember {
            derivedStateOf {
                password.length > 7
            }
        }

        var isPasswordVisible by remember {
            mutableStateOf(false)
        }
        val focusManager = LocalFocusManager.current
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                label = {
                    Text(text = "Name")
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                isError = !isUserNameValid
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "abc@domain.com") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Address"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = !isEmailValid,
                trailingIcon = {
                    if (email.isNotBlank()) {
                        IconButton(
                            onClick = { email = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear email"
                            )
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email Address"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = !isPasswordValid,
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(id = R.drawable.visibility) else painterResource(
                                id = R.drawable.visibility_off
                            ),
                            contentDescription = "Toggle password Visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                enabled = isEmailValid && isPasswordValid,
                onClick = {
                    viewModel.signUp(userName, email, password)
                },
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(12.dp), text = "Sign Up", textAlign = TextAlign.Center
                )
                when (val result = viewModel.signUpState.value) {
                    is Resource.Error -> {
                        Toast.makeText(LocalContext.current, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(14.dp)
                                .border(
                                    shape = MaterialTheme.shapes.medium,
                                    color = Violet30,
                                    width = 4.dp
                                )
                                .fillMaxSize()
                        )
                    }
                    is Resource.Success -> {
                        if (result.data == true) {
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {
                          navController.navigate(Screens.LoginScreen.route){
                              popUpTo(Screens.LoginScreen.route)
                          }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Sign In")
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}