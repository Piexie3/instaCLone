package com.example.instaclone.feature_post.domain.use_cases

import com.example.instaclone.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class UploadPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(
        postImage: String,
        postDescription: String,
        time: Long,
        userName: String,
        userImage: String
    ) = repository.uploadPost(
        postImage = postImage,
        postDescription = postDescription,
        time = time,
        userName = userName,
        userImage = userImage
    )
}