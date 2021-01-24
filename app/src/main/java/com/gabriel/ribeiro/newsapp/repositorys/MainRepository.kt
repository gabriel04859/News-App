package com.gabriel.ribeiro.newsapp.repositorys

import androidx.lifecycle.LiveData
import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response

interface MainRepository {

    suspend fun getHeadlines(country: String, pageNumber: Int) :Response<NewResponse>

}
