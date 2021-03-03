package com.gabriel.ribeiro.newsapp.repositorys

import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImple @Inject constructor(private val dataSource: DataSource) : SearchRepository {
    override suspend fun searchForNews(searchQuery: String, pageNumber: Int): Response<NewResponse>
    = dataSource.searchForNews(searchQuery, pageNumber)
}