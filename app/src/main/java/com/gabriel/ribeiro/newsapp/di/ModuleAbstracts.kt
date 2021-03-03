package com.gabriel.ribeiro.newsapp.di

import com.gabriel.ribeiro.newsapp.repositorys.HeadlinesRepository
import com.gabriel.ribeiro.newsapp.repositorys.HeadlinesRepositoryImple
import com.gabriel.ribeiro.newsapp.repositorys.SearchRepository
import com.gabriel.ribeiro.newsapp.repositorys.SearchRepositoryImple
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAbstracts {



    @Singleton
    @Binds
    abstract fun bindSearchRepository(searchRepositoryImple: SearchRepositoryImple): SearchRepository

    @Binds
    @Singleton
    abstract fun bindHeadlineRepository(headlinesRepositoryImple: HeadlinesRepositoryImple) : HeadlinesRepository



    /*@Binds
    abstract fun binMainRepository(mainRepositoryImplemented: MainRepositoryImplemented) : MainRepository*/

}




