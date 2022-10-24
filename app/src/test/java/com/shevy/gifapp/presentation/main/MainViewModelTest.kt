package com.shevy.gifapp.presentation.main

import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.Gif
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class MainViewModelTest {
/*
    @AfterEach
    fun afterEach() {
        Mockito.reset(interactorTest)
        //ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @BeforeEach
    fun beforeEach() {
      ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }*/

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSearchText() = runBlocking {
        // setup
        val interactorTest = mock<GifsInteractorImpl>()

        val testData = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val text = "Test"
        val result = mock<Deferred<List<Gif>>>()
        `when`(result.await()).thenReturn(testData)
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(result)
        val viewModel = MainViewModel(interactorTest)

        //Mockito.verify(interactorTest, Mockito.times(1)).getSearchingGifs(text)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        val actual = viewModel.searchText.value.toString()
        Assertions.assertEquals(text, actual)
    }
}