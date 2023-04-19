package com.example.instaclone.feature_user.domain.use_cases

data class ProfileScreenUseCases(
    val createOrUpdateProfileToFirebase: CreateOrUpdateProfileToFirebase,
    val loadProfileFromFirebase: LoadProfileFromFirebase,
    val setUserStatusToFirebase: SetUserStatusToFirebase,
    val uploadPictureToFirebase: UploadPictureToFirebase
)