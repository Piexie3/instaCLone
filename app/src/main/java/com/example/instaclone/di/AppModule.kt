package com.example.instaclone.di

import com.example.instaclone.feature_auth.data.repository.AuthRepositoryImpl
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_auth.domain.use_cases.*
import com.example.instaclone.feature_post.data.repository.PostRepositoryImpl
import com.example.instaclone.feature_post.domain.repository.PostRepository
import com.example.instaclone.feature_post.domain.use_cases.GetAllPostsUseCases
import com.example.instaclone.feature_post.domain.use_cases.PostUseCases
import com.example.instaclone.feature_post.domain.use_cases.UploadPostUseCase
import com.example.instaclone.feature_user.data.repository.UserRepositoryImpl
import com.example.instaclone.feature_user.domain.repository.UserRepository
import com.example.instaclone.feature_user.domain.use_cases.user_use_cases.GetUserDetailsUseCase
import com.example.instaclone.feature_user.domain.use_cases.user_use_cases.SetUserDetailsUseCase
import com.example.instaclone.feature_user.domain.use_cases.user_use_cases.UserUseCases
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
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            IsUserAuthenticated(repository),
            FirebaseAuthState(repository),
            SignInUseCase(repository),
            SignOutUseCase(repository),
            SignUpUsecase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesUserRepository(fireStore: FirebaseFirestore): UserRepository{
        return UserRepositoryImpl(fireStore)
    }

    @Provides
    @Singleton
    fun providesUserUseCases(repository: UserRepository): UserUseCases{
        return UserUseCases(
            GetUserDetailsUseCase(repository),
            SetUserDetailsUseCase(repository)
        )
    }
    @Provides
    @Singleton
    fun providesPostRepository(fireStore: FirebaseFirestore): PostRepository{
        return PostRepositoryImpl(fireStore)
    }

    @Provides
    @Singleton
    fun providesPostUseCases(repository: PostRepository): PostUseCases{
        return PostUseCases(
            GetAllPostsUseCases(repository),
            UploadPostUseCase(repository)
        )
    }
}