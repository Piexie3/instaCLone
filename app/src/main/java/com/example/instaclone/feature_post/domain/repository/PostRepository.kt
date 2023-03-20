package com.example.instaclone.feature_post.domain.repository

import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPosts(): Flow<Resource<List<Post>>>
    fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userName: String,
        userImage: String
    ): Flow<Resource<Boolean>>
}