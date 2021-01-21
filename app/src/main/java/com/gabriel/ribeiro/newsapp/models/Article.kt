package com.gabriel.ribeiro.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gabriel.ribeiro.newsapp.models.responses.Source

@Entity( tableName = "articles")
data class Article(
        @PrimaryKey(autoGenerate = true)
        val id : Int? = null,
        val author : String,
        val content : String,
        val description : String,
        val publishedAt : String,

        val source : Source,
        val title : String,
        val url : String,
        val urlToImage : String
        ) {
}