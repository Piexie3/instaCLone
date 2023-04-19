package com.example.instaclone.feature_post.domain.repository

import android.net.Uri
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun uploadPostImageToFirebase(url: Uri): Flow<Resource<String>>

    suspend fun createPostToFirebase(post: Post): Flow<Resource<Boolean>>

    suspend fun loadPostsFromFirebase(): Flow<Resource<List<Post>>>
}