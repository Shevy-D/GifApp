package com.shevy.gifapp.domain

import com.shevy.gifapp.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GifsInteractor(
    private val api: ApiInterface
) {
    /*
    filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        filter["q"] = searchText
        filter["limit"] = "20"
        filter["offset"] = "0"
        filter["rating"] = "g"
        filter["lang"] = "en"
     */
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getTrendingGifs(): Deferred<List<Gif>> = scope.async {
        val response = api.getGifsSuspend(apiKey, limit, "g")
        val gifs = response.data.map {
            Gif(it.images.downsized.url, it.images.original.url)
        }
        return@async gifs
    }

    companion object {
        private const val apiKey = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        private const val limit = 20

        fun create(): GifsInteractor {
            return GifsInteractor(ApiInterface.create())
        }
    }
}

class Gif(val previewUrl: String, val url: String)