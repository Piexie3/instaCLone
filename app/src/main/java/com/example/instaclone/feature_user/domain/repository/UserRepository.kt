package com.example.instaclone.feature_user.domain.repository

import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_user.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserDetails(userId: String): Flow<Resource<User?>>
    fun setUserDetails(
        userId: String,
        name: String,
        userName: String,
        bio: String,
        webUrl:String
    ): Flow<Resource<Boolean>>
}