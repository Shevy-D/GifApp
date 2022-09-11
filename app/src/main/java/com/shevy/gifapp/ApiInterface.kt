package com.shevy.gifapp

import com.shevy.gifapp.data.GiphyDC
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

//TODO rename GifsApi
interface ApiInterface {

    //TODO rename getTrendingGifs
    @GET("v1/gifs/trending")
    fun getGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
    ): Call<GiphyDC>

    @GET("v1/gifs/trending")
    suspend fun getGifsSuspend(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
    ): GiphyDC

    // TODO явно перечислить все параметры запроса как в getGifs
    // rename searchGifs
    @GET("v1/gifs/search")
    fun getGifsHashMapSearch(@QueryMap filter: HashMap<String, String>): Call<GiphyDC>

    companion object {
        var BASE_URL = "https://api.giphy.com/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                // TODO gson->moshi
                // TODO GsonConverterFactory -> MoshiConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}