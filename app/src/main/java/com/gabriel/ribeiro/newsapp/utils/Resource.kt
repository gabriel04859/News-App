package com.gabriel.ribeiro.newsapp.utils

import java.lang.Exception

sealed class Resource<T>(val data : T? = null,
                         val exception: Exception? = null,
                         val message : String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Failure<T>(exception: Exception? = null, message: String? = null, data : T? = null) : Resource<T>(data,exception,message)
    class Loading<T> : Resource<T>()
}