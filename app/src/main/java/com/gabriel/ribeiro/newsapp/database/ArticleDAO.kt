package com.gabriel.ribeiro.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gabriel.ribeiro.newsapp.models.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticlesSaved() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}