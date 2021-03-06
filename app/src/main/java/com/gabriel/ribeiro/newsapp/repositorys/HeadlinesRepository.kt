package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

interface HeadlinesRepository {

    suspend fun getHeadlines(country: String, pageNumber: Int) : Response<NewResponse>

    suspend fun getNewsForCategory(country: String, category : String) : Response<NewResponse>

    suspend fun saveArticle(article: Article) : Long

    suspend fun deleteArticle(article: Article)

    fun getAllArticleSaved() : LiveData<List<Article>>

}
