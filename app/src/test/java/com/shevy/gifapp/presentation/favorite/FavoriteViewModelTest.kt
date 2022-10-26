package com.shevy.gifapp.presentation.favorite

import android.app.Application
import android.content.Context
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepository
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import com.shevy.gifapp.data.room.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class FavoriteViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get all favorites test`() {
        // setup
        val application = mock<Application>()
        val favoriteViewModelTest = FavoriteViewModel(application)

        //val favoriteMock = mock<Flow<List<Favorite>>>()

        val favoriteViewModelMock = mock<FavoriteViewModel>()

        val favorites: Flow<List<Favorite>> =
            flowOf(
                (listOf(
                    Favorite(downsized = "789", original = "321"),
                    Favorite(downsized = "123", original = "456")
                ))
            )

        val contextTest = mock<Context>()
        //val daoNoteTest = FavoriteDatabase.getInstance(contextTest).getFavoriteDao()
        //val repository: FavoriteRepository = FavoriteRepositoryImpl(daoNoteTest)

        val favoriteRepository = mock<FavoriteRepositoryImpl>()

        `when`(favoriteRepository.allFavorites).thenReturn(favorites)

        `when`(favoriteViewModelMock.initDatabase()).doAnswer {
            val daoNote = FavoriteDatabase.getInstance(contextTest).getFavoriteDao()
            val repository = FavoriteRepositoryImpl(daoNote)
        }

        //action
        favoriteViewModelTest.initDatabase()
        val actual = favoriteViewModelTest.getAllFavorites()

        //check
        Assertions.assertEquals(favorites, actual)
    }
}