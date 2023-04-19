package com.example.instaclone.feature_post.data.repository

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.instaclone.core.utils.Constants
import com.example.instaclone.core.utils.Constants.COLLECTION_NAME_POSTS
import com.example.instaclone.core.utils.Constants.ERROR_MESSAGE
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.repository.PostRepository
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.models.UserStatus
import com.example.instaclone.feature_user.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PostRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage,
    private val authRepository: AuthRepository
) : PostRepository {
    override suspend fun uploadPostImageToFirebase(url: Uri): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val uuidImage = UUID.randomUUID()
        val imageName = "images/$uuidImage.jpg"
        val storageRef = storage.reference.child(imageName)

        storageRef.putFile(url).apply {

        }.await()
        var downloadUrl = ""
        storageRef.downloadUrl.addOnSuccessListener {
            downloadUrl = it.toString()
        }.await()
        emit(Resource.Success(downloadUrl))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createPostToFirebase(post: Post): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid.toString()
            val userEmail = authRepository.currentUser?.email.toString()
            val databaseReference =
                database.getReference(COLLECTION_NAME_POSTS).child(userUUID).child("post")

            val childUpdates = HashMap<String, String>()

            childUpdates["/postUUID/"] = userUUID
            childUpdates["/userEmail/"] = userEmail
            if (post.postImage != "") childUpdates["/postImage/"] = post.postImage
            if (post.postDescription != "") childUpdates["/postDescription/"] = post.postDescription
            if (post.userImage != "") childUpdates["/userImage/"] = post.userImage
            if (post.userName != "") childUpdates["/userName/"] = post.userName

            childUpdates["/time/"] = System.currentTimeMillis().toString()


            databaseReference.setValue(childUpdates).addOnSuccessListener{
                Log.d("PostData", it.toString())
            }
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }
    override suspend fun loadPostsFromFirebase(): Flow<Resource<List<Post>>> =
        callbackFlow {
            try {
                val myUUID = auth.currentUser?.uid
                val databaseReference = database.getReference(COLLECTION_NAME_POSTS)

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val postList = mutableListOf<Post>()
                        launch {
                            for (i in snapshot.children) {
                                val post = i.getValue(Post::class.java)
                                if (post != null) {
                                    if (post.postUUID != myUUID) {
                                        postList += post
                                    }
                                }
                            }
                            launch {
                                val postlist = mutableListOf<Post>()

                                for (i in postList) {
                                    val postImage: String = i.postImage
                                    val postDescription: String = i.postDescription
                                    val postUUID: String = i.postUUID
                                    val userImage: String = i.userImage
                                    val userName: String = i.userName
                                    val time: Long? = i.time

                                    val post = Post(
                                        postUUID = postUUID,
                                        postImage = postImage,
                                        postDescription = postDescription,
                                        userName = userName,
                                        userImage = userImage,
                                        time = time
                                    )
                                    postlist += post
                                }
                                launch {
                                    val resultList = mutableListOf<Post>()

                                    val asyncTask = async {
                                        for (i in postlist) {
                                            database
                                                .getReference(COLLECTION_NAME_POSTS)
                                                .get()
                                                .addOnSuccessListener {
                                                    it.children.forEach {
                                                        val post = Post(
                                                            postUUID = i.postUUID,
                                                            postImage = i.postImage,
                                                            userName = i.userName,
                                                            postDescription = i.postDescription,
                                                            userImage = i.userImage,
                                                            time = i.time
                                                        )
                                                        resultList += post
                                                    }
                                                }.addOnFailureListener {
                                                    this@callbackFlow.trySendBlocking(
                                                        Resource.Error(
                                                            it.localizedMessage ?: ERROR_MESSAGE
                                                        )
                                                    )
                                                }
                                        }
                                        delay(1000)
                                    }
                                    asyncTask.invokeOnCompletion {
                                        this@callbackFlow.trySendBlocking(
                                            Resource.Success(
                                                resultList
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Resource.Error(error.message))
                    }
                })
                awaitClose {
                    channel.close()
                    cancel()
                }
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Resource.Error(e.message ?: ERROR_MESSAGE))
            }
        }
}