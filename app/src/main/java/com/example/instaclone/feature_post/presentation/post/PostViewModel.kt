package com.example.instaclone.feature_post.presentation.post

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_post.domain.models.Post
import com.example.instaclone.feature_post.domain.models.PostRegister
import com.example.instaclone.feature_post.domain.use_cases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
) : ViewModel() {
    var toastMessage = mutableStateOf("")
        private set
    var isLoading = mutableStateOf(false)
        private set
    var post: List<PostRegister> by mutableStateOf(listOf())
        private set
    var postDataStateFromFirebase = mutableStateOf(Post())
        private set

    fun uploadPictureToFirebase(uri: Uri) {
        viewModelScope.launch {
            postUseCases.uploadPost(uri).collect {response->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        //Picture Uploaded
                        isLoading.value = false
                        response.data?.let {
                            Post(postImage = it)
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }
    }

    fun createPostToFirebase(post: Post) {
        viewModelScope.launch {
            postUseCases.createPost(post).collect { response ->
                when(response){
                    is Resource.Loading -> {
                        toastMessage.value = ""
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        if(response.data == true){
                            toastMessage.value = "Profile Updated"
                        }else{
                            toastMessage.value = "Profile Saved"
                        }
                        //delay(2000) //Bu ne içindi hatırlayamadım.
                        loadostFromFirebase()
                    }
                    is Resource.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }




    private fun loadostFromFirebase() {
        viewModelScope.launch {
            postUseCases.loadPost().collect { response ->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        postDataStateFromFirebase.value = response.data!!
                        delay(500)
                        isLoading.value = false
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

//    fun createPostRoom(){
//        viewModelScope.launch {
//            postUseCases.createPostRoomToFirebase().collect{result->
//                when(result){
//                    is Resource.Loading -> {
//                        isLoading.value = true
//                    }
//                    is Resource.Success -> {
//                        delay(500)
//                    }
//                    is Resource.Error -> {}
//                }
//
//            }
//        }
//    }
}

