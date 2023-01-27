package com.example.instaclone.feature_auth.domain.repository

import com.example.bettol.core.utils.Resource
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(userName: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
}

