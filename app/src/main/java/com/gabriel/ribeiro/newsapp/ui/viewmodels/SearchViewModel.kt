package com.gabriel.ribeiro.newsapp.ui.viewmodels

import androidx.lifecycle.*
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.SearchRepository
import com.gabriel.ribeiro.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {

    private val searchQueryData = MutableLiveData<String>()

    fun searchForNews(query: String) {
        searchQueryData.value = query
    }

    val searchNews = searchQueryData.distinctUntilChanged().switchMap { query ->
        liveData<Resource<NewResponse>>(Dispatchers.IO) {
            emit(Resource.Loading())
            val response = searchRepository.searchForNews(query, 1)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }

            } else {
                emit(Resource.Failure(exception = Exception(response.message())))

            }

        }

    }
}