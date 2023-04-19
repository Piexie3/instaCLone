package com.example.instaclone.feature_post.domain.use_cases

import com.example.instaclone.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class LoadPost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke()=
        repository.loadPostsFromFirebase()
}