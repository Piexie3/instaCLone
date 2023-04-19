package com.example.instaclone.feature_user.presentation.profile


import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaclone.core.utils.Resource
import com.example.instaclone.feature_user.domain.models.User
import com.example.instaclone.feature_user.domain.models.UserStatus
import com.example.instaclone.feature_user.domain.use_cases.ProfileScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: ProfileScreenUseCases
) : ViewModel() {
    var toastMessage = mutableStateOf("")
        private set
    var isLoading = mutableStateOf(false)
        private set

    var isUserSignOutState = mutableStateOf(false)
        private set

    var userDataStateFromFirebase = mutableStateOf(User())
        private set

    init {
        loadProfileFromFirebase()
    }

    //PUBLIC FUNCTIONS

    fun setUserStatusToFirebaseAndSignOut(userStatus: UserStatus){
        viewModelScope.launch {
            useCases.setUserStatusToFirebase(userStatus).collect{ response ->
                when(response){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if(response.data == true){
//                            signOut()
                        }else{
                            //Auth.currentuser null.
                        }

                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun uploadPictureToFirebase(uri: Uri) {
        viewModelScope.launch {
            useCases.uploadPictureToFirebase(uri).collect {response->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        //Picture Uploaded
                        isLoading.value = false
                        response.data?.let {
                            User(imageUrl = it)
                        }?.let {
                            updateProfileToFirebase(
                                it
                            )
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }
    }

    fun updateProfileToFirebase(myUser: User) {
        viewModelScope.launch {
            useCases.createOrUpdateProfileToFirebase(myUser).collect { response ->
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
                        loadProfileFromFirebase()
                    }
                    is Resource.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }




    private fun loadProfileFromFirebase() {
        viewModelScope.launch {
            useCases.loadProfileFromFirebase().collect { response ->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        userDataStateFromFirebase.value = response.data!!
                        delay(500)
                        isLoading.value = false
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

}