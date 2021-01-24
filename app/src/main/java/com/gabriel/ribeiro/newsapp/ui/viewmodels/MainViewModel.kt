package com.gabriel.ribeiro.newsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.MainRepository
import com.gabriel.ribeiro.newsapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.Exception

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){
    var headLines = MutableLiveData<Result<NewResponse>>()

    init {
        getHeadlines("us")
    }
    private fun getHeadlines(countryCode : String) = viewModelScope.launch {
        headLines.postValue(Result.Loading())
        val response = mainRepository.getHeadlines(countryCode,1)
        headLines.postValue(handlerHeadLines(response))
    }

    private fun handlerHeadLines(response: Response<NewResponse>) : Result<NewResponse>{
            if (response.isSuccessful){
                response.body()?.let {
                    return  Result.Success(it)
                }
            }
        return Result.Failure(message = response.message())

    }
    /*fun getAllArticles() : LiveData<Result<List<Article>>>{
        viewModelScope.launch(Dispatchers.IO){
            val response = mainRepository.getHeadlines("us",1)
            try{
                newResponses.postValue(Result.Loading())
                if (response.isSuccessful){
                    response.body()?.let {
                        newResponses.postValue(Result.Success(it.articles))
                    }
                }

            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    newResponses.value = Result.Failure(e)
                }
            }
        }
        return newResponses
    }*/


}