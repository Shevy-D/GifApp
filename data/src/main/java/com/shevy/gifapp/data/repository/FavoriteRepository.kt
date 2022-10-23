package com.shevy.gifapp.data.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.data.models.database.Favorite

interface FavoriteRepository {

    val allFavorites: LiveData<List<Favorite>>

    suspend fun insertFavorite(favorite: Favorite)

    suspend fun getFavoriteByUrl(url: String): Favorite

    suspend fun deleteFavoriteByUrl(url: String)

    suspend fun deleteAllFavorites()
}