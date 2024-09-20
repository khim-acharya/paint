package com.example.portablemoviestudio.api

import com.example.portablemoviestudio.data.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 通信APIのInstancesを作成
object ApiInstances {
    private fun getMovieInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
            GsonConverterFactory.create()).build()
    }
    val movieApiInstance: MovieApi = getMovieInstance().create(MovieApi::class.java)
}