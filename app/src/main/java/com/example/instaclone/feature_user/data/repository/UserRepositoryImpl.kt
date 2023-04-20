package com.example.instaclone.feature_user.data.repository

import android.net.Uri
import android.util.Log
import com.example.instaclone.core.utils.Constants
import com.example.instaclone.core.utils.Constants.COLLECTION_NAME_USERS
import com.example.instaclone.core.utils.Constants.ERROR_MESSAGE
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.models.UserStatus
import com.example.instaclone.feature_user.domain.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : UserRepository {

    override suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Resource<Boolean>> =flow{
        try {
            emit(Resource.Loading())
            if (authRepository.currentUser != null) {
                val userUUID = authRepository.currentUser?.uid.toString()

                val databaseReference =
                    database.getReference(COLLECTION_NAME_USERS).child(userUUID).child("user")
                        .child("status")
                databaseReference.setValue(userStatus.toString()).await()
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>> = flow{
        try {
            emit(Resource.Loading())
            val uuidImage = UUID.randomUUID()
            val imageName = "profileImages/$uuidImage.jpg"
            val storageRef = storage.reference.child(imageName)

            storageRef.putFile(url).apply {}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
            emit(Resource.Success(downloadUrl))


        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Resource<Boolean>> = flow{
        try {
            emit(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid.toString()
            val userEmail = authRepository.currentUser?.email.toString()
            val databaseReference =
                database.getReference(COLLECTION_NAME_USERS).child(userUUID).child("user")

            val childUpdates = mutableMapOf<String, Any>()

            childUpdates["/profileUUID/"] = userUUID
            childUpdates["/userEmail/"] = userEmail
            if (user.webUrl != "") childUpdates["/webUrl/"] = user.webUrl
            if (user.userName != "") childUpdates["/userName/"] = user.userName
            if (user.imageUrl != "") childUpdates["/imageUrl/"] =
                user.imageUrl
            if (user.userSurName != "") childUpdates["/userSurName/"] = user.userSurName
            if (user.bio != "") childUpdates["/bio/"] = user.bio
            if (user.url != "") childUpdates["/url/"] = user.url
            if (user.webUrl != "") childUpdates["/webUrl/"] = user.webUrl
            if (user.userPhoneNumber != "") childUpdates["/userPhoneNumber/"] =
                user.userPhoneNumber
            childUpdates["/status/"] = UserStatus.ONLINE.toString()


            databaseReference.updateChildren(childUpdates).addOnSuccessListener{
                Log.d("UserData", it.toString())
            }.await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadProfileFromFirebase(): Flow<Resource<User>> = callbackFlow{
        try {
            this@callbackFlow.trySendBlocking(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid
            val databaseReference = database.getReference(COLLECTION_NAME_USERS)
            val postListener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userFromFirebaseDatabase =
                        snapshot.child(userUUID!!).child("user").getValue(User::class.java)
                            ?: User()
                    this@callbackFlow.trySendBlocking(Resource.Success(userFromFirebaseDatabase))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Resource.Error(error.message))
                }
            })
            databaseReference.addValueEventListener(postListener)
            awaitClose {
                databaseReference.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }
}


