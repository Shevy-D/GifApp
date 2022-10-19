package com.shevy.gifapp.room.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.room.data.FavoriteDao
import com.shevy.gifapp.room.model.Favorite

class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    override val allFavorites: LiveData<List<Favorite>>
        get() = favoriteDao.getAllFavorites()

    override suspend fun insertFavorite(favorite: Favorite, onSuccess: () -> Unit) {
       favoriteDao.insertFavorite(favorite)
        onSuccess()
    }

    override suspend fun deleteFavorite(favorite: Favorite, onSuccess: () -> Unit) {
        favoriteDao.deleteFavorite(favorite)
        onSuccess()
    }

    override suspend fun deleteAll(onSuccess: () -> Unit) {
        favoriteDao.deleteAll()
        onSuccess()
    }

    /*    val allFavorite: LiveData<List<Favorite>> = favoriteDao.getAllFavorites()

fun getAllFavorites(): LiveData<List<Favorite>> {
    return favoriteDao.getAllFavorites()
}

suspend fun insertFavorite(favorite: Favorite) {
    favoriteDao.insertFavorite(favorite)
}

suspend fun deleteFavorite(favorite: Favorite){
    favoriteDao.deleteFavorite(favorite)
}

suspend fun deleteAll(){
    favoriteDao.deleteAll()
}*/
}