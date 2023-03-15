package com.example.instaclone.feature_post.presentation.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.use_cases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
): ViewModel() {
    private val _postData = mutableStateOf<Resource<List<Post>>>(Resource.Loading())
    val postData : State<Resource<List<Post>>> = _postData

    private val _uploadPostData = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val uploadPostData : State<Resource<Boolean>> = _uploadPostData

    fun getAllPosts(userId: String){
        viewModelScope.launch {
            postUseCases.getAllPostsUseCases(userId).collect{
                _postData.value = it
            }
        }
    }
    fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userId: String,
        userName: String,
        userImage: String
    ){
        viewModelScope.launch {
            postUseCases.uploadPostUseCase(postImage, postDescription, time, userId, userName, userImage).collect{
                _uploadPostData.value = it
            }
        }
    }

}







