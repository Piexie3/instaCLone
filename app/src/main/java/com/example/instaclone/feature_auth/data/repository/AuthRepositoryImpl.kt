package com.example.instaclone.feature_auth.data.repository

import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_user.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser>? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        userName: String
    ): Resource<FirebaseUser>? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val obj = User(userName = userName, password = password, userEmail = email)
//            result?.user?.email?.let {
//                result.user?.verifyBeforeUpdateEmail(it)
//            }
//            result?.user?.updateProfile(
//                userProfileChangeRequest { setDisplayName(userName).build() }
//            )?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    override suspend fun logout() {
        return auth.signOut()
    }
}