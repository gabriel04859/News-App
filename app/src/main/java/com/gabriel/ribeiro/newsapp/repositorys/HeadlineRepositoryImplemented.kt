package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.api.NewsApi
import com.gabriel.ribeiro.newsapp.database.ArticleDAO
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

class HeadlineRepositoryImplemented(private val dataSource: DataSource) : HeadlineRepository {
    override suspend fun getHeadlines(country: String, pageNumber: Int, apiKey: String): Response<NewResponse> {
        return dataSource.getHeadlines(country,pageNumber)
    }
}