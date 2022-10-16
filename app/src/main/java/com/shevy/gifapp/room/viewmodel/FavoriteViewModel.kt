package com.shevy.gifapp.room.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.shevy.gifapp.room.database.FavoriteDatabase
import com.shevy.gifapp.room.model.Favorite
import com.shevy.gifapp.room.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application/*, private val favRepo: FavoriteRepository*/) :
    AndroidViewModel(application) {

    private val _allFavorites = MutableLiveData<List<Favorite>>()
    val allFavorites: LiveData<List<Favorite>> = _allFavorites

    private val _favorite = MutableLiveData<Favorite>()
    val favorite: LiveData<Favorite> = _favorite

    private var favoriteRepository: FavoriteRepository =
        FavoriteRepository(FavoriteDatabase.getDatabase(application).getFavoriteDao())

    suspend fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.insertFavorite(favorite)
        }
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        favoriteRepository.deleteFavorite(favorite)
    }

    suspend fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.deleteAll()
        }
    }
}