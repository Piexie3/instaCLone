package com.example.instaclone.core.composables

import android.os.Build
import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.google.type.DateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
val usersCurrentDate: String = getCurrentDate()


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH) //"yyyy-MM-dd"
    return current.format(formatter)
}