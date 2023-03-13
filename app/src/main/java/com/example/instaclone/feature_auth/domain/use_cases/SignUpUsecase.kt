package com.example.instaclone.feature_auth.domain.use_cases

import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String, userName: String) =
        authRepository.signup(userName, email, password)
}