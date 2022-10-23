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
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) :
    AndroidViewModel(application) {

    lateinit var repository: FavoriteRepository
    val context = application

    fun initDatabase() {
        val daoNote = FavoriteDatabase.getInstance(context).getFavoriteDao()
        repository = FavoriteRepositoryImpl(daoNote)
    }

    fun getAllFavorites(): LiveData<List<Favorite>> {
        return repository.allFavorites
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite)
        }
    }

/*    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorite)
        }
    }*/

/*    fun getFavorite(favorite: Favorite): LiveData<Favorite> {
        return repository.getFavorite(favorite.id)
    }*/

    suspend fun getByUrl(url: String): Favorite {
        return viewModelScope.async(Dispatchers.IO) {
            return@async repository.getByUrl(url)
        }.await()
    }

    fun deleteByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByUrl(url)
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}