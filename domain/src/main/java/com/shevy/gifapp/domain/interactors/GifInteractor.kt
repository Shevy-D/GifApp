package com.shevy.gifapp.domain.interactors

import kotlinx.coroutines.Deferred

interface GifInteractor {

    fun getTrendingGifs(): Deferred<List<Gif>>

    fun getSearchingGifs(q: String): Deferred<List<Gif>>
}