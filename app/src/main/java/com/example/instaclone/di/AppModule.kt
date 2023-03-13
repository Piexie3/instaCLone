package com.example.instaclone.di

import com.example.instaclone.feature_auth.data.repository.AuthRepositoryImpl
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_auth.domain.use_cases.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth, fireStore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, fireStore)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            IsUserAuthenticated(repository),
            FirebaseAuthState(repository),
            SignInUseCase(repository),
            SignOutUseCase(repository),
            SignUpUsecase(repository)
        )
    }
}