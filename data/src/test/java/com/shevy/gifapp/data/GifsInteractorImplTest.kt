package com.shevy.gifapp.data

import com.shevy.gifapp.data.models.gif.Gifs
import com.shevy.gifapp.data.models.gif.GiphyDC
import com.shevy.gifapp.data.models.gif.Images
import com.shevy.gifapp.domain.interactors.Gif
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

internal class GifsInteractorImplTest {

    val interactorTest = mock<GifsInteractorImpl>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() {
        Mockito.reset(interactorTest)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTrendingGifsTest() = runBlocking {
        //setup

        val api = mock<GifsApi>()
        val testData = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val testGiphyDC = mock<GiphyDC>()
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(gifs.await()).thenReturn(testData)
        //`when`(api.getTrendingGifs(any(), any(), any())).thenReturn(testGiphyDC)
        `when`(interactorTest.getTrendingGifs()).thenReturn(gifs)

        //action
        val actual = GifsInteractorImpl(api).getTrendingGifs().await()

        //check
        Assertions.assertEquals(testData, actual)

    }

    @Test
    fun getSearchingGifsTest() {
        //setup

        //action

        //check

    }
}