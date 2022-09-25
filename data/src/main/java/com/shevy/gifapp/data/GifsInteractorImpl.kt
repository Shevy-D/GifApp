package com.shevy.gifapp.data

import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GifsInteractorImpl(private val api: GifsApi): GifInteractor {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getTrendingGifs(): Deferred<List<Gif>> = scope.async {
        val response = api.getTrendingGifs(apiKey, limit, rating)
        val gifs = response.data.map {
            Gif(it.images.downsized.url, it.images.original.url)
        }
        return@async gifs
    }

    override fun getSearchingGifs(q: String): Deferred<List<Gif>> = scope.async {
        val response = api.getSearchingGifs(apiKey, q, limit, offset, rating, lang)
        val gifs = response.data.map {
            Gif(it.images.downsized.url, it.images.original.url)
        }
        return@async gifs
    }

    companion object {
        private const val apiKey = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        private const val limit = 20
        private const val offset = "0"
        private const val rating = "g"
        private const val lang = "en"


        fun create(): GifsInteractorImpl {
            return GifsInteractorImpl(GifsApi.create())
        }
    }
}

