package com.example.instaclone.feature_auth.domain.repository

import com.example.instaclone.core.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    val currentUser: FirebaseUser?
    fun isUserAuthenticated(): Boolean

    fun getUserAuthState(): Flow<Boolean>
     fun login(email: String, password: String): Flow<Resource<Boolean>>
     fun signup(userName: String, email: String, password: String): Flow<Resource<Boolean>>
    fun logout(): Flow<Resource<Boolean>>
}

