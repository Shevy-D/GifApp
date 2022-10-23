package com.shevy.gifapp.presentation.favorite

import android.app.Application
import androidx.lifecycle.*
import com.shevy.gifapp.data.room.database.FavoriteDatabase
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.data.repository.FavoriteRepository
import com.shevy.gifapp.data.repository.FavoriteRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) :
    AndroidViewModel(application) {

    lateinit var repository: FavoriteRepository
    val context = application

    fun initDatabase(){
        val daoNote = FavoriteDatabase.getInstance(context).getFavoriteDao()
        repository = FavoriteRepositoryImpl(daoNote)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>{
        return repository.allFavorites
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite)
        }
    }

/*    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorite)
        }
    }*/

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