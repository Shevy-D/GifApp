package com.shevy.gifapp.data.repository

import androidx.lifecycle.LiveData
import com.shevy.gifapp.data.models.database.Favorite

interface FavoriteRepository {

    val allFavorites: LiveData<List<Favorite>>

    suspend fun insertFavorite(favorite: Favorite)

    //suspend fun updateFavorite(favorite: Favorite)

    //fun getFavorite(id: Int): LiveData<Favorite>

    //suspend fun getByUrl(url: String): Favorite

    suspend fun getByUrl(url: String): Favorite

    suspend fun deleteByUrl(url: String)

    suspend fun deleteAll()
}