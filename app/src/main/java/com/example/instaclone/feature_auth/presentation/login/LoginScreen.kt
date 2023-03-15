package com.example.instaclone.feature_auth.presentation.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instaclone.R
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.presentation.AuthViewModel
import com.example.instaclone.navigation.Screens
import com.example.instaclone.ui.theme.DarkGreen10
import com.example.instaclone.ui.theme.Violet30
import com.example.instaclone.ui.theme.White20

@Composable
fun LoginScreen(
    navController: NavController
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(White20)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val focusManager = LocalFocusManager.current

            val email = remember {
                mutableStateOf("")
            }
            val password = remember {
                mutableStateOf("")
            }

            val isEmailValid by remember {
                derivedStateOf {
                    Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                }
            }

            val isPasswordValid by remember {
                derivedStateOf {
                    password.value.length > 7
                }
            }

            var isPasswordVisible by remember {
                mutableStateOf(false)
            }

            Text(
                text = "Welcome Back...",
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,

                )
            Text(
                text = "... to world of enjoyment",
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
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
                    if (email.value.isNotBlank()) {
                        IconButton(
                            onClick = { email.value = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear email"
                            )
                        }
                    }
                },
                singleLine = true
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
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
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {

                    },
                ) {
                    Text(
                        text = "Forgotten Password?"
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.signIn(email.value, password.value)
                },
                enabled = isEmailValid && isPasswordValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen10)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                when (val result = viewModel.signInState.value) {
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
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
                        if (result.data==true) {
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }



            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screens.SignUpScreen.route) {
                            launchSingleTop = true
                        }
                    },
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Don't have an account?")
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Sign Up")
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

    }
}
