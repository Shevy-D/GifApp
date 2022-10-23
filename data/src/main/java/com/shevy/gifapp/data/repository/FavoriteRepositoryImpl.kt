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

/*    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }*/

    override suspend fun deleteByUrl(url: String) {
        favoriteDao.deleteByUrl(url)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}