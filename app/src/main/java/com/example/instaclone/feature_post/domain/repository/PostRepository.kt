package com.example.instaclone.feature_post.domain.repository

import android.net.Uri
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_user.domain.models.User
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>>
    suspend fun createPostToFirebase(user: Post): Flow<Resource<Boolean>>
    suspend fun loadPostFromFirebase(): Flow<Resource<Post>>
}