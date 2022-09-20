package com.shevy.gifapp.domain

import com.shevy.gifapp.data.models.GiphyDC
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
    ): GiphyDC

    @GET("v1/gifs/search")
    suspend fun getSearchingGifs(
        @Query("api_key") apiKey: String,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: String,
        @Query("rating") rating: String,
        @Query("lang") lang: String
    ): GiphyDC

    companion object {
        var BASE_URL = "https://api.giphy.com/"

        fun create(): GifsApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GifsApi::class.java)
        }
    }
}