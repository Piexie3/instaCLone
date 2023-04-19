package com.example.instaclone.feature_user.domain.use_cases

import com.example.instaclone.feature_user.domain.repository.UserRepository

class LoadProfileFromFirebase(
    private val profileScreenRepository: UserRepository
) {
    suspend operator fun invoke() = profileScreenRepository.loadProfileFromFirebase()
}