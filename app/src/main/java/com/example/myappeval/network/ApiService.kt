package com.example.myappeval.network

import com.example.myappeval.model.ApiResponse
import com.example.myappeval.model.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(@Query("page") page: Int = 1): ApiResponse<Character>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}