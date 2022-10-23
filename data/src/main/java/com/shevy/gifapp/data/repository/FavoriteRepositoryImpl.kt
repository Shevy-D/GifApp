package com.shevy.gifapp.data.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.data.room.data.FavoriteDao
import com.shevy.gifapp.data.models.database.Favorite

class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    override val allFavorites: LiveData<List<Favorite>>
        get() = favoriteDao.getAllFavorites()

    override suspend fun insertFavorite(favorite: Favorite) {
       favoriteDao.insertFavorite(favorite)
    }

    override suspend fun getFavoriteByUrl(url: String): Favorite {
        return favoriteDao.getFavoriteByUrl(url)
    }

    override suspend fun deleteFavoriteByUrl(url: String) {
        favoriteDao.deleteFavoriteByUrl(url)
    }

    override suspend fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }
}