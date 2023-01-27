package com.example.instaclone.feature_auth.data.repository

import com.example.bettol.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result= auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun signup(
        userName: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
       return try {
            val result= auth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(userProfileChangeRequest { setDisplayName(userName).build() })?.await()
            Resource.Success(result.user!!)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }


}



