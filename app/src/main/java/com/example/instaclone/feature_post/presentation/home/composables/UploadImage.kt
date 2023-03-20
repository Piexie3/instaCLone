package com.example.instaclone.feature_post.presentation.upload_sample

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.R
import com.example.instaclone.feature_post.presentation.post.PostViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

@Composable
fun UploadImage(
    postViewModel: PostViewModel = hiltViewModel()
) {
    val uploaddata = postViewModel.uploadPostData.value
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { imageUri = it }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            if (imageUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUri!!)
                        .error(R.drawable.ic_broken_image)
                        .crossfade(true)
                        .fallback(R.drawable.placeholder)
                        .build(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        else {
            Image(
                painter = painterResource(id = R.drawable.post),
                contentDescription = "Select Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }


        if (imageUri != null) {
            Button(
                onClick = { uploadImageToFirebase(context, imageUri!!) }) {
                Text("Upload Image")
            }
        }else{
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Select Image")
            }
        }
    }
}

private fun uploadImageToFirebase(context: Context, imageUri: Uri) {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images/${imageUri.lastPathSegment}")
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val buffer = ByteArrayOutputStream()

    inputStream?.use { input ->
        buffer.use { output ->
            input.copyTo(output)
        }
    }

    val data = buffer.toByteArray()
    val uploadTask = imagesRef.putBytes(data)

    uploadTask.addOnSuccessListener {
        // Image uploaded successfully
    }.addOnFailureListener {
        // Image upload failed
    }
}
