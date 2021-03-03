package com.gabriel.ribeiro.newsapp.repositorys

import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

interface SearchRepository {

    suspend fun searchForNews(searchQuery : String, pageNumber: Int) : Response<NewResponse>
}