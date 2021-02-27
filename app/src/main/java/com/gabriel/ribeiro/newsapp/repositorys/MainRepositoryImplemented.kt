package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

class MainRepositoryImplemented(private val dataSource: DataSource ) : MainRepository {

    override suspend fun getHeadlines(country: String, pageNumber: Int): Response<NewResponse> {
        return dataSource.getHeadlines(country, pageNumber)
    }

    override suspend fun searchForNews(searchQuery: String, pageNumber: Int): Response<NewResponse> =
            dataSource.searchForNews(searchQuery, pageNumber)

    override suspend fun saveArticle(article: Article): Long = dataSource.saveArticle(article)

    override suspend fun deleteArticle(article: Article)  = dataSource.deleteArticle(article)

    override fun getAllArticleSaved(): LiveData<List<Article>> = dataSource.getAllArticleSaved()


}