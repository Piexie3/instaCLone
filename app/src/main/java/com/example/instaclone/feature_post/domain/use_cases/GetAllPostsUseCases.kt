package com.example.instaclone.feature_post.domain.use_cases

import com.example.instaclone.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPostsUseCases @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getAllPosts()
}