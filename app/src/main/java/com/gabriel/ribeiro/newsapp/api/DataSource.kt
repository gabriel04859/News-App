package com.gabriel.ribeiro.newsapp.api

import com.gabriel.ribeiro.newsapp.database.ArticleDatabase
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

class DataSource(private val articleDatabase: ArticleDatabase) {
    suspend fun getHeadlines(country : String, pageNumber  : Int)
    = RetrofitInstance.newsApi.getHeadlines(country,pageNumber)
    suspend fun searchForNews(country: String, pageNumber: Int)
    = RetrofitInstance.newsApi.searForNews(country, pageNumber)

    suspend fun saveArticle(article : Article) =  articleDatabase.getArticleDAO().saveArticle(article)

    suspend fun deleteArticle(article: Article) = articleDatabase.getArticleDAO().deleteArticle(article)

    fun getAllArticleSaved() = articleDatabase.getArticleDAO().getAllArticlesSaved()

}