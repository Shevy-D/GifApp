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
    fun `check that getTrendingGifs() method return right value`() = runBlocking {
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
    }

    @Test
    fun `check that the getTrendingGifs() method is called one time`() {
        //setup
        val interactor = mock<GifsInteractorImpl>()

        //action
        interactor.getTrendingGifs()

        //check
        Mockito.verify(interactor, Mockito.times(1)).getTrendingGifs()
    }

    @Test
    fun `check that getSearchingGifs() method return right value`() = runBlocking {
        //setup
        val api = mock<GifsApi>()
        val original = Original("", "", "", "", "", "", "test url", "", "", "")
        val original2 = Original("", "", "", "", "", "", "test url2", "", "", "")
        val fixedWidthDownsampled = FixedWidthDownsampled("", "", "test preview url", "", "", "")
        val fixedWidthDownsampled2 = FixedWidthDownsampled("", "", "test preview url2", "", "", "")
        val images = Images(original, fixedWidthDownsampled)
        val images2 = Images(original2, fixedWidthDownsampled2)
        val response = GiphyDC(listOf(Gifs(images), Gifs(images2)))
        val text = "Test"

        `when`(api.getSearchingGifs(any(), any(), any(), any(), any(), any())).thenReturn(response)

        val expected = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )

        //action
        val actual = GifsInteractorImpl(api).getSearchingGifs(text).await()

        //check
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `check that the getSearchingGifs() method is called one time`() {
        //setup
        val interactor = mock<GifsInteractorImpl>()
        val text = "Test"

        //action
        interactor.getSearchingGifs(text)

        //check
        Mockito.verify(interactor, Mockito.times(1)).getSearchingGifs(text)
    }
}