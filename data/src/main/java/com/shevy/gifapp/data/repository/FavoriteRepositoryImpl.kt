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

/*    override suspend fun updateFavorite(favorite: Favorite) {
        favoriteDao.updateFavorite(favorite)
    }*/

/*    override fun getFavorite(id: Int): LiveData<Favorite> {
        return favoriteDao.getFavorite(id)
    }*/

/*    override suspend fun getByUrl(url: String): Favorite {
        return favoriteDao.getByUrl(url)
    }*/

    override suspend fun getByUrl(url: String): Favorite {
        return favoriteDao.getByUrl(url)
    }

    override suspend fun deleteByUrl(url: String) {
        favoriteDao.deleteByUrl(url)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}