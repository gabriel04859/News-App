package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response

interface HeadlineRepository {


    suspend fun getHeadlines(country: String, pageNumber: Int, apiKey: String = API_KEY) :Response<NewResponse>

}
