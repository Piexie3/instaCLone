package com.example.instaclone.feature_user.domain.use_cases.user_use_cases

import com.example.instaclone.feature_user.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String) = repository.getUserDetails(userId)
}