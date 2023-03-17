package com.example.instaclone.feature_user.data.repository

import com.example.instaclone.core.utils.Constans
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
   authRepository: AuthRepository
): UserRepository {
    private var operationalSuccessful: Boolean = false
    val userId = authRepository.currentUser!!.uid
    override fun getUserDetails(userId: String): Flow<Resource<User?>> = callbackFlow{
       Resource.Loading(true)
        val snapshotListener = firebaseFirestore.collection(Constans.COLLECTION_NAME_USERS)
            .document(userId)
            .addSnapshotListener{ snapshot, error ->
                val result = if (snapshot !=null){
                    val userInfo =snapshot.toObject(User::class.java)
                    Resource.Success(userInfo)
                }else{
                    Resource.Error(error?.message?: error.toString())
                }
                trySend(result).isSuccess
            }
        awaitClose{
            snapshotListener.remove()
        }
    }

    override fun setUserDetails(
        name: String,
        userName: String,
        bio: String,
        webUrl: String,
        ): Flow<Resource<Boolean>> = flow{
        operationalSuccessful = false
        try {
            val userObj = mutableMapOf<String,String>()
            userObj["name"] = name
            userObj["userName"] = userName
            userObj["bio"] = bio
            userObj["webUrl"] = webUrl
            firebaseFirestore.collection(Constans.COLLECTION_NAME_USERS).document(userId).update(
                userObj as Map<String, Any>
            )
                .addOnSuccessListener {

                }.await()
            if (operationalSuccessful){
                emit(Resource.Success(operationalSuccessful))
            }else{
                emit(Resource.Error("Edit does not Succeed"))
            }
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage?: "Unexpected Error Occurred"))
        }
    }
}


