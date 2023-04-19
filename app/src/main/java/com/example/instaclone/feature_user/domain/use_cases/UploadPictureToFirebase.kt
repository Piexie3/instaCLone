package com.example.instaclone.feature_user.domain.use_cases

import android.net.Uri
import com.example.instaclone.feature_user.domain.repository.UserRepository

class UploadPictureToFirebase(
    private val profileScreenRepository: UserRepository
) {
    suspend operator fun invoke(url: Uri) = profileScreenRepository.uploadPictureToFirebase(url)
}