package com.gabriel.ribeiro.newsapp.api

import com.gabriel.ribeiro.newsapp.database.ArticleDatabase
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class DataSource @Inject constructor(private val articleDatabase: ArticleDatabase) {

    suspend fun getHeadlines(country : String, pageNumber  : Int)
    = RetrofitInstance.newsApi.getHeadlines(country,pageNumber)

    suspend fun searchForNews(searchQuery: String, pageNumber: Int)
    = RetrofitInstance.newsApi.searForNews(searchQuery, pageNumber)

    suspend fun getNewsForCategory(country: String, category: String)
    = RetrofitInstance.newsApi.getNewsForCategory(country, category)

    suspend fun saveArticle(article : Article) =  articleDatabase.getArticleDAO().saveArticle(article)

    suspend fun deleteArticle(article: Article) = articleDatabase.getArticleDAO().deleteArticle(article)

    fun getAllArticleSaved() = articleDatabase.getArticleDAO().getAllArticlesSaved()



}