package com.shevy.gifapp.presentation.main

import com.shevy.gifapp.data.GifsInteractorImpl
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

class MainViewModelTest {

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
    fun `enters the search text and puts it in the StateFlow`() = runBlocking {
        // setup
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(gifs)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        val actual = viewModel.searchText.value
        Assertions.assertEquals(text, actual)
    }

    @Test
    fun `check that the getSearchingGifs() method is called once`() {
        // setup
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(gifs)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        Mockito.verify(interactorTest, Mockito.times(1)).getSearchingGifs(any())
    }

    @Test
    fun `check that the getTrendingGifs() method is never called`() {
        // setup
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(gifs)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        Mockito.verify(interactorTest, Mockito.never()).getTrendingGifs()
    }

    @Test
    fun `enters the search text and get list of GIFS`() = runBlocking {
        // setup
        val testData = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(gifs.await()).thenReturn(testData)
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(gifs)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        val actual = viewModel.gifs.value
        Assertions.assertEquals(testData, actual)
    }

    @Test
    fun `enters the search text and get loading = true`() = runBlocking {
        // setup
        val text = "Test"
        val gifs = mock<Deferred<List<Gif>>>()
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(gifs)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        val actual = viewModel.loading.value

        Mockito.verify(interactorTest, Mockito.times(1))
            .getSearchingGifs(text)
        Assertions.assertEquals(true, actual)
    }
}