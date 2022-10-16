package com.shevy.gifapp.presentation.main

import com.shevy.gifapp.data.GifsApi
import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlinx.coroutines.async
import org.mockito.Mockito
import org.mockito.kotlin.mock

/*class TestGifsInteractorImpl : GifInteractor {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getTrendingGifs(): Deferred<List<Gif>> {
        TODO("Not yet implemented")
    }

    override fun getSearchingGifs(q: String): Deferred<List<Gif>> = scope.async {
        return@async listOf(Gif("test preview url", "test url"))
    }

}*/

class MainViewModelTest {

    @Test
    suspend fun `Should return correct data`() {

        val interactorTest = mock<GifInteractor>()
        val text = "Test"

        val testData = listOf(Gif("test preview url", "test url"))
        Mockito.`when`(interactorTest.getSearchingGifs(text).await()).thenReturn((testData))

        val expected = listOf(Gif("test preview url", "test url"))
        val actual = interactorTest.getSearchingGifs(text).await()

        Assertions.assertNotNull(actual)
        Assertions.assertEquals(expected, actual)

/*        val expected = gifs
        val actual = 2 + 2
        Assertions.assertEquals(expected, actual)*/
    }
}