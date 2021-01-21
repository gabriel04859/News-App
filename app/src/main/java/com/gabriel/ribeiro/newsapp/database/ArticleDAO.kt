package com.gabriel.ribeiro.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gabriel.ribeiro.newsapp.models.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<Article>>

    @Delete
    fun deleteArticle(article: Article)
}