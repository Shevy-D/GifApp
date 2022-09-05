package com.shevy.gifapp

import com.shevy.gifapp.data.GiphyDC
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

/*    @GET("v1/gifs/trending?api_key=Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx&limit=25&rating=g")
    fun getGifs(): Call<GiphyDC>*/

    @GET("v1/gifs/trending?api_key=Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx&limit=25&rating=g")  /*&limit=25&rating=g*/
    fun getGifs(/*@Query("api_key") sort: String*/): Call<GiphyDC>

    companion object {
        var BASE_URL = "https://api.giphy.com/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}