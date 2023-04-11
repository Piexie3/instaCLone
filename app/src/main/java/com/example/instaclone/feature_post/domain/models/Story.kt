package com.example.instaclone.feature_post.domain.models

data class Story(
    val title: String,
    val imageUrl: String,
    val isSeen: Boolean = false
)
