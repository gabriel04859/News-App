package com.gabriel.ribeiro.newsapp.di

import android.content.Context
import androidx.room.Room
import com.gabriel.ribeiro.newsapp.database.ArticleDatabase
import com.gabriel.ribeiro.newsapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModulesProvides {

    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context, ArticleDatabase::class.java, DATABASE_NAME
    ).build()
}