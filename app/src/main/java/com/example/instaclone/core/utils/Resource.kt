package com.example.bettol.core.utils

sealed class Resource<T>(val data: T?=null, val message: String? = null){
    class Success<T>(data:T): Resource<T>(data)
    class Error<T>(val exception: Exception): Resource<T>()
    class Loading<T>(data:T?=null): Resource<T>(data)
}