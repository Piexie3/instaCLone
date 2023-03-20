package com.example.instaclone.feature_post.data.repository

import com.example.instaclone.core.utils.Constans
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.repository.PostRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    authRepository: AuthRepository
) : PostRepository {
    private var operationSuccessful = false

    val userId = authRepository.currentUser!!.uid
    override fun getAllPosts(): Flow<Resource<List<Post>>> = callbackFlow {
        Resource.Loading(true)
        val snapshotListener = firestore.collection(Constans.COLLECTION_NAME_POSTS)
            .whereNotEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                val result = if (snapshot != null) {
                    val postList = snapshot.toObjects(Post::class.java)
                    Resource.Success<List<Post>>(postList)
                } else {
                    Resource.Error(error?.message ?: error.toString())
                }
                trySend(result).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userName: String,
        userImage: String
    ): Flow<Resource<Boolean>> = flow {
        operationSuccessful = false
        try {
            val postId = firestore.collection(Constans.COLLECTION_NAME_POSTS)
                .document()
                .id
            val post = Post(
                postId = postId,
                postImage = postImage,
                postDescription = postDescription,
                userImage=userImage,
                userName = userName,
                time = time
            )
            firestore.collection(Constans.COLLECTION_NAME_POSTS)
                .document(postId)
                .set(post)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if (operationSuccessful) {
                Resource.Success(operationSuccessful)
            } else {
                emit(Resource.Error("Post upload unsuccessful"))
            }
        } catch (e: Exception) {
            operationSuccessful = false
            emit(Resource.Error(e.localizedMessage ?: "An Unexpected error"))
        }
    }
}