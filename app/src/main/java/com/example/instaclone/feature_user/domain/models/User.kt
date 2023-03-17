package com.example.instaclone.feature_user.domain.models

data class User(
    var name: String = "",
    var userName: String = "",
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPost: String = "",
    var url: String = "",
    var webUrl: String = "",
    var bio: String = "",
)
