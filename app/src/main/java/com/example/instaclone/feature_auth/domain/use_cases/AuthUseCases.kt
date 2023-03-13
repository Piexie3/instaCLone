package com.example.instaclone.feature_auth.domain.use_cases

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val firebaseAuthState: FirebaseAuthState,
    val signInUseCase: SignInUseCase,
    val signOutUseCase: SignOutUseCase,
    val signUpUseCase: SignUpUsecase
)
