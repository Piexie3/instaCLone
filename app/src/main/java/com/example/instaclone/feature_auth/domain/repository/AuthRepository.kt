package com.example.instaclone.feature_auth.domain.repository

import com.example.instaclone.core.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    fun isUserAuthenticated(): Boolean
    suspend fun login(email: String, password: String): Resource<FirebaseUser>?
    suspend fun signup(email: String, password: String, userName: String): Resource<FirebaseUser>?
    suspend fun logout()
}