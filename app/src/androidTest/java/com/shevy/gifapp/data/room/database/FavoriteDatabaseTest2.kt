package com.shevy.gifapp.data.room.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import com.shevy.gifapp.data.room.data.FavoriteDao
import com.shevy.gifapp.presentation.favorite.FavoriteViewModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

//@RunWith(AndroidJUnit4::class)
class FavoriteDatabaseTest2 : TestCase() {

    private lateinit var db: FavoriteDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FavoriteDatabase::class.java).build()
        dao = db.getFavoriteDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun insertAndReadFavorite() = runBlocking {
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

        //action
        favoriteViewModel.initDatabase()
        favoriteViewModel.getAllFavorites().collect {
            actual = it
        }

        //check
        Assertions.assertEquals(favorites, actual)


        /*val favorite = Favorite(downsized = "downsized", original = "original")
        dao.insertFavorite(favorite = favorite)
        var result = listOf(Favorite(downsized = "", original = ""))
        dao.getAllFavorites().collect {
            result = it
        }
        assertThat(result.contains(favorite)).isTrue()*/
    }
}