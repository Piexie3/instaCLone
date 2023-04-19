package com.example.instaclone.feature_user.domain.repository

import android.net.Uri
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.models.UserStatus
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Resource<Boolean>>
    suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>>
    suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Resource<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Resource<User>>
}