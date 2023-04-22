package com.example.instaclone.feature_post.domain.use_cases

import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class CreatePost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(post: Post) = repository.createPostToFirebase(post)
}
