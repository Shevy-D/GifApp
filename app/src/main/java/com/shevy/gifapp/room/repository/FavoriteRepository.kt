package com.shevy.gifapp.room.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.room.data.FavoriteDao
import com.shevy.gifapp.room.model.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavorite: LiveData<List<Favorite>> = favoriteDao.getAllFavorites()

    suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavoriteToRoomDatabase(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite){
        favoriteDao.deleteFavorite(favorite)
    }

    suspend fun deleteAll(){
        favoriteDao.deleteAll()
    }
}