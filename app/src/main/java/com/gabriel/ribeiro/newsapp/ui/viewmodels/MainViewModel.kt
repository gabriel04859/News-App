package com.gabriel.ribeiro.newsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.MainRepository
import com.gabriel.ribeiro.newsapp.utils.Constants.Companion.TAG
import com.gabriel.ribeiro.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){
    private var _headLines = MutableLiveData<Resource<NewResponse>>()
    val headLines : LiveData<Resource<NewResponse>> get() = _headLines
    var breakingNewsResponse : NewResponse? = null

    private var _searchNews = MutableLiveData<Resource<NewResponse>>()
    val searchNews : LiveData<Resource<NewResponse>> get() = _searchNews
    var searchNewsResponse : NewResponse? = null

    init {
        getHeadlines()
    }
     fun getHeadlines(countryCode  : String = "br") = viewModelScope.launch {
        _headLines.postValue(Resource.Loading())
        val response = mainRepository.getHeadlines(countryCode,1)
        _headLines.postValue(handlerHeadLines(response))

    }

    fun searchForNews(query : String, pageNumber : Int = 1) = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
       val response = mainRepository.searchForNews(query,pageNumber)
        _searchNews.postValue(handlerHeadLines(response))
    }

    private fun handlerHeadLines(response: Response<NewResponse>) : Resource<NewResponse>{
            if (response.isSuccessful){
                response.body()?.let {
                    Log.d(TAG, "handlerHeadLines: $it")
                    return  Resource.Success(it)
                }
            }
        return Resource.Failure(message = response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        mainRepository.saveArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        mainRepository.deleteArticle(article)
    }

    fun getArticlesSaved() = mainRepository.getAllArticleSaved()

}