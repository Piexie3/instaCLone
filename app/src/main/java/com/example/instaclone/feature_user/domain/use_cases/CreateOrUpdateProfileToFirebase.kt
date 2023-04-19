package com.example.instaclone.feature_user.domain.use_cases

import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.repository.UserRepository

class CreateOrUpdateProfileToFirebase(
    private val profileScreenRepository: UserRepository
) {
    suspend operator fun invoke(user: User) =
        profileScreenRepository.createOrUpdateProfileToFirebase(user)
}