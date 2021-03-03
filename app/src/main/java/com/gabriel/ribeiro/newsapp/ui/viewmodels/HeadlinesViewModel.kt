package com.gabriel.ribeiro.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.HeadlinesRepository
import com.gabriel.ribeiro.newsapp.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class HeadlinesViewModel @Inject constructor(private val headlinesRepository: HeadlinesRepository) : ViewModel() {

    fun setHeadlines() = liveData<Resource<NewResponse>>() {
        emit(Resource.Loading())
        val response = headlinesRepository.getHeadlines("br", 1)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it))
            }
        } else {
            emit(Resource.Failure(exception = Exception(response.message())))
        }
    }

    fun getNewsOfCategory(category: String) = liveData<Resource<NewResponse>> {
        emit(Resource.Loading())
        val response = headlinesRepository.getNewsForCategory("br", category)
        if (response.isSuccessful){
            response.body()?.let { newResponse ->
                emit(Resource.Success(newResponse))
            }
        }else{
            emit(Resource.Failure(exception = Exception(response.message())))
        }
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        headlinesRepository.saveArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        headlinesRepository.deleteArticle(article)
    }

    fun getAllArticlesSaved() = headlinesRepository.getAllArticleSaved()
}