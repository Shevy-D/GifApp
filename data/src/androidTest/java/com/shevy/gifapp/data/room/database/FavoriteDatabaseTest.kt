package com.shevy.gifapp.data.room.database

import android.content.Context
import androidx.room.Room
import com.google.common.truth.Truth.assertThat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.room.data.FavoriteDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDatabaseTest : TestCase() {
    private lateinit var db: FavoriteDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FavoriteDatabase::class.java).build()
        dao = db.getFavoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadFavorite() = runBlocking {
        val favorite = Favorite(downsized = "downsized", original = "original")
        dao.insertFavorite(favorite = favorite)
        var result = listOf(Favorite(downsized = "", original = ""))
        dao.getAllFavorites().collect {
            result = it
        }
        assertThat(result.contains(favorite)).isTrue()
    }
}