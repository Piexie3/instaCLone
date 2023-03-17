package com.example.instaclone.di

import com.example.instaclone.feature_auth.data.repository.AuthRepositoryImpl
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_auth.domain.use_cases.AuthUseCases
import com.example.instaclone.feature_auth.domain.use_cases.IsUserAuthenticated
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
        return AuthRepositoryImpl(auth,fireStore)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            isUserAuthenticated = IsUserAuthenticated(authRepository),
//            firebaseAuthState = FirebaseAuthState(authRepository),
//            signInUseCase = SignInUseCase(authRepository),
//            signOutUseCase = SignOutUseCase(authRepository),
//            signUpUseCase = SignUpUsecase(authRepository)
        )
    }

    @Provides
    @Singleton
    fun providesUserRepository(fireStore: FirebaseFirestore,authRepository: AuthRepository): UserRepository{
        return UserRepositoryImpl(fireStore, authRepository)
    }

    @Provides
    @Singleton
    fun providesUserUseCases(repository: UserRepository): UserUseCases{
        return UserUseCases(
            getUserDetailsUseCase = GetUserDetailsUseCase(repository),
            setUserDetailsUseCase = SetUserDetailsUseCase(repository)
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
            getAllPostsUseCases = GetAllPostsUseCases(repository),
            uploadPostUseCase = UploadPostUseCase(repository)
        )
    }
}