package com.example.instaclone.feature_post.domain.use_cases

data class PostUseCases(
    val createPost: CreatePost,
    val uploadPost: UploadPostUseCase,
    val loadPost: LoadPost,
//    val createPostRoomToFirebase: CreatePostRoomToFirebase
)
