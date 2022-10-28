package com.shevy.gifapp.data

import com.shevy.gifapp.data.models.gif.GiphyDC
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifsByApi(
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

//        fun create(): GifsApi {
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(MoshiConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//            return retrofit.create(GifsApi::class.java)
//        }
    }
}