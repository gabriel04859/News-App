package com.gabriel.ribeiro.newsapp.api

import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import retrofit2.Response

class DataSource {
    suspend fun getHeadlines(country : String, pageNumber  : Int) : Response<NewResponse>{
      return RetrofitInstance.newsApi.getHeadlines(country,pageNumber)
    }
}