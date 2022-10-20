package com.shevy.gifapp.presentation.main

import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

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