package com.example.portablemoviestudio.api

import com.example.portablemoviestudio.data.urlPath
import com.example.portablemoviestudio.entities.Movies
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// 通信APIのretrofitのinterfaceを作成
interface MovieApi {
    @GET(urlPath)
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ):retrofit2.Response<Movies>
}