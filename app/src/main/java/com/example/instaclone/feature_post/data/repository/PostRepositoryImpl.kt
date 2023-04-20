package com.example.instaclone.feature_post.data.repository

import android.net.Uri
import android.util.Log
import com.example.instaclone.core.utils.Constants.COLLECTION_NAME_POSTS
import com.example.instaclone.core.utils.Constants.ERROR_MESSAGE
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.repository.PostRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
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

class PostRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage,
    private val authRepository: AuthRepository
) : PostRepository {
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

    override suspend fun createPostToFirebase(post: Post): Flow<Resource<Boolean>> = flow{
        try {
            emit(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid.toString()
            val userEmail = authRepository.currentUser?.email.toString()
            val databaseReference =
                database.getReference(COLLECTION_NAME_POSTS).child(userUUID).child("post")

            val childUpdates = hashMapOf<String, Any>()

            childUpdates["/profileUUID/"] = userUUID
            childUpdates["/userEmail/"] = userEmail
            if (post.userName != "") childUpdates["/userName/"] = post.userName
            if (post.postImage != "") childUpdates["/postImage/"] =
                post.postImage
            if (post.postDescription != "") childUpdates["/postDescription/"] = post.postDescription

            databaseReference.push().setValue(childUpdates).addOnSuccessListener{
                Log.d("postDaata", it.toString())
            }.await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadPostFromFirebase(): Flow<Resource<Post>> = callbackFlow{
        try {
            this@callbackFlow.trySendBlocking(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid
            val databaseReference = database.getReference(COLLECTION_NAME_POSTS)
            val postListener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val postFromFirebaseDatabase =
                        snapshot.child(userUUID!!).child("post").getValue(Post::class.java)
                            ?: Post()
                    this@callbackFlow.trySendBlocking(Resource.Success(postFromFirebaseDatabase))
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