package com.example.instaclone.feature_auth.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signInState: State<Resource<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signUpState: State<Resource<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signOutState: State<Resource<Boolean>> = _signOutState

    private val _firebaseAuthState = mutableStateOf(false)
    val firebaseAuthState: State<Boolean> = _firebaseAuthState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.signInUseCase(email, password).collect {
                _signUpState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, userName: String) {
        viewModelScope.launch {
            authUseCases.signUpUseCase(email, password, userName).collect {
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOutUseCase().collect {
                _signOutState.value = it
                if (it == Resource.Success(true)) {
                    _signInState.value = Resource.Success(false)
                }
            }
        }
    }

    fun getFirebaseAuthstate() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState().collect {
                _firebaseAuthState.value = it
            }
        }
    }
}