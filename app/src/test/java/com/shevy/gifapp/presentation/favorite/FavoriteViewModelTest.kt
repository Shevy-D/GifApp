package com.shevy.gifapp.presentation.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepository
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import com.shevy.gifapp.data.room.data.FavoriteDao
import com.shevy.gifapp.data.room.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class FavoriteViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get all favorites test`() = runBlocking {
        // setup
        val application = mock<Application>()
        val favoriteViewModel = FavoriteViewModel(application)

        lateinit var actual: List<Favorite>

        //val favoriteViewModelTest = mock<FavoriteViewModel>()

        val favorites: List<Favorite> =
            (listOf(
                Favorite(downsized = "789", original = "321"),
                Favorite(downsized = "123", original = "456")
            ))

        val favoriteRepository = mock<FavoriteRepositoryImpl>()

        val favoriteDatabase = mock<FavoriteDatabase>()
        val favoriteDao = mock<FavoriteDao>()

        //`when`(favoriteDao).thenReturn()
        `when`(favoriteDatabase.getFavoriteDao()).thenReturn(favoriteDao)

/*
        `when`(favoriteDao.getAllFavorites()).thenReturn(favorites)
        `when`(favoriteRepository.allFavorites).thenReturn(flowOf(favorites))
        `when`(favoriteViewModelTest.getAllFavorites()).thenReturn(flowOf(favorites))
        */

        //action
        favoriteViewModel.initDatabase()
        favoriteViewModel.getAllFavorites().collect {
            actual = it
        }

        //check
        Assertions.assertEquals(favorites, actual)
    }
}