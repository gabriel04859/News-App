package com.gabriel.ribeiro.newsapp.ui.viewmodels.factors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabriel.ribeiro.newsapp.repositorys.HeadlineRepository
import com.gabriel.ribeiro.newsapp.repositorys.HeadlineRepositoryImplemented
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel

@Suppress("UNCHECKED_CAST")
class HeadlinesFactors(private val repository: HeadlineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeadlinesViewModel(repository) as T
    }
}