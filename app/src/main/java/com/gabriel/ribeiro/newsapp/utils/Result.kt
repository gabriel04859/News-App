package com.gabriel.ribeiro.newsapp.utils

import java.lang.Exception

sealed class Result<T>(val data : T? = null,
                       val exception: Exception? = null,
                       val message : String? = null){
    class Success<T>(data: T) : Result<T>(data)
    class Failure<T>(exception: Exception? = null, message: String? = null, data : T? = null) : Result<T>(data,exception,message)
    class Loading<T> : Result<T>()
}