package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response
import javax.inject.Inject

class HeadlinesRepositoryImple @Inject constructor(private val dataSource: DataSource) : HeadlinesRepository {

    override suspend fun getHeadlines(country: String, pageNumber: Int): Response<NewResponse>
    = dataSource.getHeadlines(country, pageNumber)

    override suspend fun getNewsForCategory(country: String, category: String): Response<NewResponse>
    = dataSource.getNewsForCategory(country, category)

    override suspend fun saveArticle(article: Article): Long = dataSource.saveArticle(article)

    override suspend fun deleteArticle(article: Article) = dataSource.deleteArticle(article)

    override fun getAllArticleSaved(): LiveData<List<Article>> = dataSource.getAllArticleSaved()
}