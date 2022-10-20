package com.shevy.gifapp.data.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shevy.gifapp.data.models.database.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_database")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_database")
    suspend fun deleteAll()
}