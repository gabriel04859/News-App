package com.gabriel.ribeiro.newsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.HeadlineRepository
import com.gabriel.ribeiro.newsapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HeadlinesViewModel(private val repository: HeadlineRepository) : ViewModel() {
    private var _articlesList = MutableLiveData<List<Article>>()
    val articleList : LiveData<List<Article>> get() = _articlesList
    var newResponses : MutableLiveData<Result<NewResponse>> = MutableLiveData()



     fun getAllArticles() {
         viewModelScope.launch(Dispatchers.IO) {
             val response = repository.getHeadlines("us", 1)
             try {
                 newResponses.postValue(Result.Loading())
                 if (response.isSuccessful) {
                     response.body()?.let {
                         newResponses.postValue(Result.Success(it))
                     }
                 }
             }catch (e : Exception){
                 withContext(Dispatchers.Main){
                     Log.i("TESTE","${e.message}")
                     newResponses.value = Result.Failure(e)
                 }
             }

         }
     }
}




