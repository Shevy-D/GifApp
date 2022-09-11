package com.shevy.gifapp

import com.shevy.gifapp.data.GiphyDC
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {

    // delete it
    @GET("v1/gifs/trending")
    fun trendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
    ): Call<GiphyDC>

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
    ): GiphyDC

    @GET("v1/gifs/search")
    fun getSearchingGifs(
        @Query("api_key") apiKey: String,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: String,
        @Query("rating") rating: String,
        @Query("lang") lang: String
    ): GiphyDC

    // delete it
    @GET("v1/gifs/search")
    fun searchingGifs(
        @Query("api_key") apiKey: String,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: String,
        @Query("rating") rating: String,
        @Query("lang") lang: String
    ): Call<GiphyDC>

    companion object {
        var BASE_URL = "https://api.giphy.com/"

        fun create(): GifsApi {
            val retrofit = Retrofit.Builder()
                // TODO gson->moshi
                // TODO GsonConverterFactory -> MoshiConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GifsApi::class.java)
        }
    }
}