package com.example.instaclone.feature_user.domain.use_cases

import com.example.instaclone.feature_user.domain.models.UserStatus
import com.example.instaclone.feature_user.domain.repository.UserRepository

class SetUserStatusToFirebase(
    private val profileScreenRepository: UserRepository
) {
    suspend operator fun invoke(userStatus: UserStatus) =
        profileScreenRepository.setUserStatusToFirebase(userStatus)
}