package com.shevy.gifapp.presentation.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepository
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import com.shevy.gifapp.data.room.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) :
    AndroidViewModel(application) {

    lateinit var repository: FavoriteRepository
    val context = application

    fun initDatabase() {
        val daoNote = FavoriteDatabase.getInstance(context).getFavoriteDao()
        repository = FavoriteRepositoryImpl(daoNote)
    }

    fun getAllFavorites(): Flow<List<Favorite>> {
        return repository.allFavorites
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite)
        }
    }

    suspend fun getFavoriteByUrl(url: String): Favorite {
        return viewModelScope.async(Dispatchers.IO) {
            return@async repository.getFavoriteByUrl(url)
        }.await()
    }

    fun deleteFavoriteByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteByUrl(url)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }
    }
}