package com.example.instaclone.feature_auth.data.repository

import com.example.instaclone.core.utils.Constans
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_posts.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthRepository {

    var operationSuccessfull: Boolean = false

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getUserAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }


    override  fun login(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            operationSuccessfull = false
            try {
                emit(Resource.Loading())
                val result = auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    operationSuccessfull = true
                }.await()
                emit(Resource.Success(operationSuccessfull))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
            }
//        return try {
//            val result= auth.signInWithEmailAndPassword(email, password).await()
//            Resource.Success(result.user!!)
//        }catch (e: Exception){
//            e.printStackTrace()
//            Resource.Error(e)
//        }
        }

    override  fun signup(
        userName: String,
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        operationSuccessfull = false
        try {
            emit(Resource.Loading())
             auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessfull = true
            }.await()
            if (operationSuccessfull) {
                val userId = auth.currentUser?.uid!!
                val obj =
                    User(userName = userName, userId = userId, password = password, email = email)
                fireStore.collection(Constans.COLLECTION_NAME_USERS).document(userId).set(obj)
                    .addOnSuccessListener {

                    }.await()
                emit(Resource.Success(operationSuccessfull))
            } else {
                Resource.Success(operationSuccessfull)
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        }
//       return try {
//            val result= auth.createUserWithEmailAndPassword(email, password).await()
//            result?.user?.updateProfile(userProfileChangeRequest { setDisplayName(userName).build() })?.await()
//            Resource.Success(result.user!!)
//        }catch (e: Exception){
//            e.printStackTrace()
//            Resource.Error(e)
//        }
    }

    override fun logout(): Flow<Resource<Boolean>> = flow {
        auth.signOut()
    }


}



