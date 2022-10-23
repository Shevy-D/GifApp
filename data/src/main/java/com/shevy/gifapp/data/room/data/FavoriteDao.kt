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

    @Update
    suspend fun updateFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_database WHERE id = :id")
    fun getFavorite(id: Int): LiveData<Favorite>

    @Query("DELETE FROM favorite_database WHERE previewUrl = :url")
    suspend fun deleteByUrl(url: String)

/*    @Query("SELECT * FROM favorite_database WHERE previewUrl = :url")
    suspend fun getByUrl(url: String): Favorite*/

    @Query("SELECT * FROM favorite_database WHERE previewUrl = :url")
    suspend fun getByUrl(url: String): Favorite

    @Query("DELETE FROM favorite_database")
    suspend fun deleteAll()
}