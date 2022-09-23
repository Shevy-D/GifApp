package com.shevy.gifapp.domain.interactors


interface GifInteractor {

    //Is it right? -> Any
    fun getTrendingGifs(): Any /*Deferred<List<Gif>>*/

    fun getSearchingGifs(q: String): Any /*Deferred<List<Gif>>*/

}