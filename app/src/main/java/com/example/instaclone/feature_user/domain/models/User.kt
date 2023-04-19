package com.example.instaclone.feature_user.domain.models

data class User(
    var profileUUID: String = "",
    var userEmail: String = "",
    var password: String = "",
    var webUrl: String = "",
    var oneSignalUserId: String = "",
    var userName: String = "",
    var imageUrl: String = "",
    var userSurName: String = "",
    var bio: String = "",
    var userPhoneNumber: String = "",
    var status: String = ""
)
