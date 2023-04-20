package com.example.instaclone.feature_post.domain.models

data class Post(
    var postUUID: String = "",
    var postImage: String = "",
    var postDescription: String = "",
    var userImage: String = "",
    var userName: String = "",
    var time: Long? = null,
)
