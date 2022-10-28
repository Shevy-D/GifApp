package com.shevy.gifapp.data

import com.shevy.gifapp.data.models.gif.*
import com.shevy.gifapp.domain.interactors.Gif
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

internal class GifsInteractorImplTest {

    @Test
    fun getTrendingGifsTest() = runBlocking {
        //setup
        val api = mock<GifsApi>()
        val original = Original("", "", "", "", "", "", "test url", "", "", "")
        val original2 = Original("", "", "", "", "", "", "test url2", "", "", "")
        val fixedWidthDownsampled = FixedWidthDownsampled("", "", "test preview url", "", "", "")
        val fixedWidthDownsampled2 = FixedWidthDownsampled("", "", "test preview url2", "", "", "")
        val images = Images(original, fixedWidthDownsampled)
        val images2 = Images(original2, fixedWidthDownsampled2)
        val response = GiphyDC(listOf(Gifs(images), Gifs(images2)))

        `when`(api.getTrendingGifsByApi(any(), any(), any())).thenReturn(response)

        val expected = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )

        //action
        val actual = GifsInteractorImpl(api).getTrendingGifs().await()

        //check
        Assertions.assertEquals(expected, actual)
        //Mockito.verify(api, Mockito.times(1)).getTrendingGifsByApi(any(), any(), any())
    }

    @Test
    fun getSearchingGifsTest() {
        //setup

        //action

        //check

    }
}