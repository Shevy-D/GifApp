package com.shevy.gifapp.presentation.favorite

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepository
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import com.shevy.gifapp.data.room.data.FavoriteDao
import com.shevy.gifapp.data.room.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class FavoriteViewModelTest {

    private lateinit var database: FavoriteDatabase
    private lateinit var favoriteDao: FavoriteDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteDatabase::class.java
        ).allowMainThreadQueries().build()

        favoriteDao = database.getFavoriteDao()

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun afterEach() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `get all favorites test`() {
        // setup
        val application = mock<Application>()
        val favoriteViewModelTest = FavoriteViewModel(application)

        val favorites: Flow<List<Favorite>> =
            flowOf(
                (listOf(
                    Favorite(downsized = "789", original = "321"),
                    Favorite(downsized = "123", original = "456")
                ))
            )

        val daoNote = FavoriteDatabase.getInstance(application).getFavoriteDao()
        val repository= /*FavoriteRepositoryImpl(daoNote)*/ mock<FavoriteRepositoryImpl>()

        val favoriteRepository = mock<FavoriteRepositoryImpl>()

        //`when`(FavoriteRepositoryImpl(daoNote)).thenReturn(repository)

        //`when`(favoriteViewModelTest.initDatabase()).thenReturn(repository)

        `when`(favoriteRepository.allFavorites).thenReturn(favorites)

        //action
        favoriteViewModelTest.initDatabase()
        val actual = favoriteViewModelTest.getAllFavorites()

        //check
        Assertions.assertEquals(favorites, actual)
    }
}