package com.gabriel.ribeiro.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.utils.Converters

@Database(
        entities = [Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDAO() : ArticleDAO

    /*,companion object{

        @Volatile
        private var INSTANCE : ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: createDatabase(context).also{
                INSTANCE = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db"
        ).build()

    }*/
}