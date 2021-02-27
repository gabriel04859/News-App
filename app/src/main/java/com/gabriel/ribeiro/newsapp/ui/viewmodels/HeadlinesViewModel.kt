package com.gabriel.ribeiro.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.repositorys.MainRepository
import com.gabriel.ribeiro.newsapp.utils.Resource
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
class HeadlinesViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun setHeadlines() = liveData<Resource<NewResponse>>(){
        emit(Resource.Loading())
        val response = mainRepository.getHeadlines("br",1)
        if (response.isSuccessful){
            response.body()?.let {
                emit(Resource.Success(it))
            }
        }else{
            emit(Resource.Failure(exception = Exception(response.message())))
        }
    }

    class HeadlinesViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HeadlinesViewModel(mainRepository) as T
        }
    }
}