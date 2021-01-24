package com.gabriel.ribeiro.newsapp.repositorys

import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

class MainRepositoryImplemented(private val dataSource: DataSource) : MainRepository {

    override suspend fun getHeadlines(country: String, pageNumber: Int): Response<NewResponse> {
        return dataSource.getHeadlines(country, pageNumber)
    }
}