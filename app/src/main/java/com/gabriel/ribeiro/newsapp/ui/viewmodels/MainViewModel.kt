package com.gabriel.ribeiro.newsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.MainRepository
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.gabriel.ribeiro.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){


    fun saveArticle(article: Article) = viewModelScope.launch {
        mainRepository.saveArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        mainRepository.deleteArticle(article)
    }

    fun getArticlesSaved() = mainRepository.getAllArticleSaved()

}