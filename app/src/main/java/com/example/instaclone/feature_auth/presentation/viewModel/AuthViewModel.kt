package com.example.instaclone.feature_auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_auth.domain.use_cases.AuthUseCases
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authUseCases: AuthUseCases
) : ViewModel() {


    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow: StateFlow<Resource<FirebaseUser>?> = _signUpFlow

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading()
            val result = authRepository.login(email, password)
            _loginFlow.value = result
        }

    }

    fun signup(email: String, password: String, userName: String) {
        viewModelScope.launch {
            _signUpFlow.value = Resource.Loading()
            val result = authRepository.signup(email, password, userName)
            _signUpFlow.value = result
        }

    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

}