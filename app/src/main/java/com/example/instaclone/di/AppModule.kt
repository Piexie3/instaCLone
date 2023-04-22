package com.example.instaclone.di

import com.example.instaclone.feature_auth.data.repository.AuthRepositoryImpl
import com.example.instaclone.feature_auth.domain.repository.AuthRepository
import com.example.instaclone.feature_auth.domain.use_cases.AuthUseCases
import com.example.instaclone.feature_auth.domain.use_cases.IsUserAuthenticated
import com.example.instaclone.feature_post.data.repository.PostRepositoryImpl
import com.example.instaclone.feature_post.domain.repository.PostRepository
import com.example.instaclone.feature_post.domain.use_cases.*
import com.example.instaclone.feature_user.data.repository.UserRepositoryImpl
import com.example.instaclone.feature_user.domain.repository.UserRepository
import com.example.instaclone.feature_user.domain.use_cases.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun providesFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth, fireStore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, fireStore)
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
    fun providesUserRepository(
        authRepository: AuthRepository,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): UserRepository {
        return UserRepositoryImpl(authRepository, database, storage)
    }


    @Provides
    @Singleton
    fun providesPostRepository(
        database: FirebaseDatabase,
        storage: FirebaseStorage,
        authRepository: AuthRepository,
        db: FirebaseFirestore
    ): PostRepository {
        return PostRepositoryImpl(
            database = database,
            storage = storage,
            authRepository = authRepository,
            db = db
        )
    }

    @Provides
    @Singleton
    fun providesPostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            createPost = CreatePost(repository),
            uploadPost = UploadPostUseCase(repository),
            loadPost = LoadPost(repository),
//            createPostRoomToFirebase = CreatePostRoomToFirebase(repository)
        )
    }

    @Provides
    fun provideProfileScreenUseCase(userRepository: UserRepository) =
        ProfileScreenUseCases(
            createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(userRepository),
            loadProfileFromFirebase = LoadProfileFromFirebase(userRepository),
            setUserStatusToFirebase = SetUserStatusToFirebase(userRepository),
            uploadPictureToFirebase = UploadPictureToFirebase(userRepository)
        )

}