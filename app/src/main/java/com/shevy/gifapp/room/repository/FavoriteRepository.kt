package com.shevy.gifapp.room.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.room.model.Favorite

interface FavoriteRepository {

    val allFavorites: LiveData<List<Favorite>>

    suspend fun insertFavorite(favorite: Favorite, onSuccess: () -> Unit)

    suspend fun deleteFavorite(favorite: Favorite, onSuccess: () -> Unit)

    suspend fun deleteAll(onSuccess: () -> Unit)
}