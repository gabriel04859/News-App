package com.gabriel.ribeiro.newsapp.ui.viewmodels

import androidx.lifecycle.*
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.MainRepository
import com.gabriel.ribeiro.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val searchQueryData = MutableLiveData<String>()

    fun searchForNews(query : String){
        searchQueryData.value = query
    }

    val searchNews = searchQueryData.distinctUntilChanged().switchMap { query ->
        liveData<Resource<NewResponse>>(Dispatchers.IO) {
            emit(Resource.Loading())
            val response = mainRepository.searchForNews(query, 1)
            if (response.isSuccessful) {
                response.body()?.let{
                    emit(Resource.Success(it))
                }

            }else{
               emit(Resource.Failure(exception = Exception(response.message())))

        }

    }

}
    class SearchViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(mainRepository) as T
        }
    }
}