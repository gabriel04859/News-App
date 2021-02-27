package com.gabriel.ribeiro.newsapp.api

import com.gabriel.ribeiro.newsapp.models.responses.NewResponse
import com.gabriel.ribeiro.newsapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadlines(@Query("country") country : String,
                            @Query("page") pageNumber : Int,
                            @Query("apiKey") apiKey : String = API_KEY) : Response<NewResponse>

    @GET("everything")
    suspend fun searForNews(@Query("q") q : String,
                            @Query("page") pageNumber: Int = 1,
                            @Query("apiKey") apiKey : String = API_KEY) : Response<NewResponse>
}
